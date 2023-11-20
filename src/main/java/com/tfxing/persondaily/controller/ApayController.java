package com.tfxing.persondaily.controller;

import com.alibaba.fastjson.JSON;
import com.lycheepay.gateway.client.GatewayClientException;
import com.lycheepay.gateway.client.InitiativePayService;
import com.lycheepay.gateway.client.KftSecMerchantService;
import com.lycheepay.gateway.client.dto.initiativepay.ActiveScanPayReqDTO;
import com.lycheepay.gateway.client.dto.initiativepay.ActiveScanPayRespDTO;
import com.lycheepay.gateway.client.dto.secmerchant.SettledSecMerchantRequestDTO;
import com.lycheepay.gateway.client.dto.secmerchant.SettledSecMerchantResponseDTO;
import com.lycheepay.gateway.client.security.KeystoreSignProvider;
import com.lycheepay.gateway.client.security.SignProvider;
import com.lycheepay.gateway.client.util.IOUtils;
import com.tfxing.persondaily.utils.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/apay")
public class ApayController {
    private static KftSecMerchantService service;
    // 初始化KftSecMerchantService
    public static void init() throws Exception {
        // 初始化证书：证书类型、证书路径、证书密码、别名、证书容器密码
        SignProvider keystoreSignProvider = new KeystoreSignProvider("PKCS12","src/main/resources/pfx.pfx", "123456".toCharArray(), null,"123456".toCharArray());
        // 签名提供者、商户服务器IP(callerIp)、国际化、对账文件或批量回盘文件下载，商户本地保存路径
        service = new KftSecMerchantService(keystoreSignProvider, "http://nqg3wp.natappfree.cc/apay/callBack", "zh_CN", "F:/zip");
        service.setHttpDomain("test-sftp.kftpay.com.cn"); // 测试环境交易请求Http地址，不设置默认为生产地址：merchant.kftpay.com.cn
        service.setConnectionTimeoutSeconds(1 * 60);// 连接超时时间（单位秒）,不设置则默认30秒
        service.setResponseTimeoutSeconds(10 * 60);// 响应超时时间（单位秒）,不设置则默认300秒
        service.setSftpAccountName("2023110700104794");//sftp账号,与商户账户编号相同（MerchantId）
        service.setSftpPassword("104794");//sftp密码 测试环境:账号后6位
        service.setSftpDomain("test-sftp.kftpay.com.cn");//sftp域名
    }
    @Test
    @GetMapping("/testRegister")
    public void testSecmerchantBaseinfoAdd() throws Exception {
        init();
        String businessMode = "面粉交易";
        String registeredFundStr = "9999999";

        SettledSecMerchantRequestDTO reqDTO = new SettledSecMerchantRequestDTO();
        reqDTO.setReqNo(String.valueOf(System.currentTimeMillis()));//请求编号
        reqDTO.setOrderNo(String.valueOf(System.currentTimeMillis()));//用于标识商户发起的一笔交易
        reqDTO.setService("amp_secmerchant_baseinfo_add");//接口名称，固定不变
        reqDTO.setVersion("1.0.0-IEST");//测试：1.0.0-IEST,生产：1.0.0-PRD
        reqDTO.setMerchantId("2023110700104794");//快付通配给商户的账户编号（测试和生产环境不同）
        reqDTO.setSecMerchantName("szywy物流科技优先公司");//二级商户名称
        reqDTO.setShortName("szywy");//会显示在用户订单信息中
        //reqDTO.setProvince(province);//可空
        //reqDTO.setCity(city);//可空
        reqDTO.setDistrict("1772");//注册地址区县编码
        reqDTO.setAddress("天利大厦704");//地址
        reqDTO.setLegalName("link");//法人姓名,如果是个人商户，填个人商户姓名
        reqDTO.setContactName("link");//联系人名称
        reqDTO.setContactPhone("19264586365");//联系人手机号
        reqDTO.setContactEmail("19264586365@163.com");//联系人邮箱
        reqDTO.setMerchantProperty("2");//商户类型：1：个人、2：企业、3：个体工商户、4：事业单位
        reqDTO.setCategory("0160550259");//经营类目
        reqDTO.setBusinessScene("卖点很普通的白面");//业务场景说明
        reqDTO.setBusinessMode(businessMode);
        reqDTO.setRegisteredFundStr(registeredFundStr);
        reqDTO.setRemark("");//可空，介绍商户营业内容等
        reqDTO.setCertPath("testCom.zip");//SFTP目录下的地址，默认在mpp目录下
        reqDTO.setCertDigest("ZuQg2d6lIa9I1OUE0Jed8w==");//上报文件签名
        reqDTO.setBusinessFunctions("[\"010101\",\"010201\"]");
        reqDTO.setSettleBankAccount("{\"settleAccountCreditOrDebit\":\"1\",\"settleBankAccountNo\":\"1120170819112111\",\"settleBankAcctType\":\"2\",\"settleBankNo\":\"1051000\",\"settleName\":\"测试-22\"}");
        //reqDTO.setPersonCertInfo(personCertInfo);
        reqDTO.setCorpCertInfo("[{\"certNo\":\"440305199109241211\",\"certType\":\"0\",\"certValidDate\":\"20991231\"},{\"certNo\":\"11311788100133ABB1\",\"certType\":\"Y\",\"certValidDate\":\"20991231\"}]");
        reqDTO.setMerchantAttribute("1");//商户属性 1实体特约商户 2 网络特约商户 3 实体兼网络特约商户
        //reqDTO.setIcpRecord("");//ICP备案号,可空,如果商户属性是2，3必填
        //reqDTO.setServiceIp("");//网站服务器IP,可空,如果商户属性是2，3必填
        //reqDTO.setCompanyWebUrl("");//经营网址,可空,如果商户属性是2，3必填
        reqDTO.setMerchantOperateName("测试(多媒体)");
        reqDTO.setMerchantMCC("4816");
        reqDTO.setMerchantEnglishName("ABC");
        reqDTO.setMerchantBankBranchNo("105337000019");
        reqDTO.setMerchantBankBranchName("中国建设银行绍兴分行");
        SettledSecMerchantResponseDTO resDTO = null;
        try {
            resDTO = service.settledSecMerchant(reqDTO);
        } catch (GatewayClientException e) {
            e.printStackTrace();
        }
        System.out.println(resDTO);
    }

    @GetMapping("/callBack")
    public void callBack() {
        System.out.println("callBack SUCCESS");
    }

    /**
     * 生成md5签名串
     */
    @Test
    public void testGenMd5FileDigest() {
        try {
            String filePath = "C:\\Users\\Administrator\\Desktop\\personal\\applicationNew\\testCom.zip";
            String certDigest = this.md5File(filePath);
            System.out.println("certDigest:"+certDigest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return 0;
    }
    /**
     * 上传sftp文件
     */
    @Test
    public void testUploadFile() {
        try {
            String localFilePath = "C:/Users/Administrator/Desktop/personal/applicationNew/testCom.zip";
            String remotePath = "/cashier/mpp";
            service.uploadFile(localFilePath, remotePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 对文件md5加密
     *
     * @author wy
     */
    private static String md5File(String uploadFile) throws FileNotFoundException, IOException {
        FileInputStream fileInputStream = new FileInputStream(uploadFile);
        byte[] md5Bytes = DigestUtils.md5(fileInputStream);
        String result = new String(Base64.encodeBase64(md5Bytes), "UTF-8");
        IOUtils.closeQuietly(fileInputStream);
        return result;
    }

}
