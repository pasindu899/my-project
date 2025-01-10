package com.aiken.pos.admin.api.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Rest API DTO
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-02-10
 */
public class CustomDeviceDto {

    // Device Props
    private Integer deviceId;
    private String bankCode;
    private String merchantName;
    private String merchantAddress;
    private BigDecimal visaNoCvmLimit;
    private BigDecimal cntactlsTrxnLimit;

    private boolean autoSettle;
    private String autoSettleTime;
    private boolean forceSettle;
    private boolean ecr;
    private boolean keyIn;
    private String status;
    private boolean ecrQr;
    private boolean signature;
    private boolean debugMode;
    private boolean noSettle;
    private boolean activityTracker;
    private boolean qrRefund;
    private boolean reversalHistory; 
    private boolean pushNotifications;
    private boolean preAuth;
    private boolean dcc;
    private boolean offline;
    
    private String network;
    
    private boolean autoReversal;
    private boolean printless;
    
    private String voidSettlePwd;
    
    private boolean disableFrgnPmntForLkrCard;

    private boolean diffSaleMidTid;


    private boolean keyInForPreAuth;

    private boolean keyInForOffline;

    private boolean enablePinPriority;

    private boolean blockMag;

    private boolean customerReceipt;
    
    //ECR 

    private CustomEcrSettingsDto ecrSettings;
    
    private CustomAuthServerConfigDto authServerSettings;

    private List<CustomMerchantDto> merchants;
    private boolean midTidSeg;
    private boolean eventAutoUpdate;

    @Override
    public String toString() {
        return "Device : [" + "deviceId:" + deviceId + "|" + "bankCode:" + bankCode + "|" + "|" + "merchantName:"
                + merchantName + "|" + "merchantAddress:" + merchantAddress + "autoSettle:" + autoSettle + "|"
                + "autoSettleTime:" + autoSettleTime + "|" + "forceSettle:" + forceSettle + "|" + "noSettle:" + noSettle +
                "|" + "ecr:" + ecr + "keyIn:" + keyIn + "]";
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public BigDecimal getVisaNoCvmLimit() {
        return visaNoCvmLimit;
    }

    public void setVisaNoCvmLimit(BigDecimal visaNoCvmLimit) {
        this.visaNoCvmLimit = visaNoCvmLimit;
    }

    public BigDecimal getCntactlsTrxnLimit() {
        return cntactlsTrxnLimit;
    }

    public void setCntactlsTrxnLimit(BigDecimal cntactlsTrxnLimit) {
        this.cntactlsTrxnLimit = cntactlsTrxnLimit;
    }

    public boolean isAutoSettle() {
        return autoSettle;
    }

    public void setAutoSettle(boolean autoSettle) {
        this.autoSettle = autoSettle;
    }

    public String getAutoSettleTime() {
        return autoSettleTime;
    }

    public void setAutoSettleTime(String autoSettleTime) {
        this.autoSettleTime = autoSettleTime;
    }

    public boolean isForceSettle() {
        return forceSettle;
    }

    public void setForceSettle(boolean forceSettle) {
        this.forceSettle = forceSettle;
    }

    public boolean isEcr() {
        return ecr;
    }

    public void setEcr(boolean ecr) {
        this.ecr = ecr;
    }

    public boolean isKeyIn() {
        return keyIn;
    }

    public void setKeyIn(boolean keyIn) {
        this.keyIn = keyIn;
    }

    public List<CustomMerchantDto> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<CustomMerchantDto> merchants) {
        this.merchants = merchants;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isEcrQr() {
        return ecrQr;
    }

    public void setEcrQr(boolean ecrQr) {
        this.ecrQr = ecrQr;
    }

    public boolean isSignature() {
        return signature;
    }

    public void setSignature(boolean signature) {
        this.signature = signature;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isNoSettle() {
        return noSettle;
    }

    public void setNoSettle(boolean noSettle) {
        this.noSettle = noSettle;
    }

	public boolean isActivityTracker() {
		return activityTracker;
	}

	public void setActivityTracker(boolean activityTracker) {
		this.activityTracker = activityTracker;
	}

	public boolean isQrRefund() {
		return qrRefund;
	}

	public void setQrRefund(boolean qrRefund) {
		this.qrRefund = qrRefund;
	}

	public boolean isReversalHistory() {
		return reversalHistory;
	}

	public void setReversalHistory(boolean reversalHistory) {
		this.reversalHistory = reversalHistory;
		}
	
	public boolean isPushNotifications() {
		return pushNotifications;
	}

	public void setPushNotifications(boolean pushNotifications) {
		this.pushNotifications = pushNotifications;
	}

	public boolean isPreAuth() {
		return preAuth;
	}

	public void setPreAuth(boolean preAuth) {
		this.preAuth = preAuth;
	}

	public boolean isDcc() {
		return dcc;
	}

	public void setDcc(boolean dcc) {
		this.dcc = dcc;
	}

	public boolean isOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	public CustomEcrSettingsDto getEcrSettings() {
		return ecrSettings;
	}

	public void setEcrSettings(CustomEcrSettingsDto ecrSettings) {
		this.ecrSettings = ecrSettings;
	}

	public CustomAuthServerConfigDto getAuthServerSettings() {
		return authServerSettings;
	}

	public void setAuthServerSettings(CustomAuthServerConfigDto authServerSettings) {
		this.authServerSettings = authServerSettings;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public boolean isAutoReversal() {
		return autoReversal;
	}

	public void setAutoReversal(boolean autoReversal) {
		this.autoReversal = autoReversal;
	}

	public boolean isPrintless() {
		return printless;
	}

	public void setPrintless(boolean printless) {
		this.printless = printless;
	}

	public boolean isDisableFrgnPmntForLkrCard() {
		return disableFrgnPmntForLkrCard;
	}

	public void setDisableFrgnPmntForLkrCard(boolean disableFrgnPmntForLkrCard) {
		this.disableFrgnPmntForLkrCard = disableFrgnPmntForLkrCard;
	}

	public String getVoidSettlePwd() {
		return voidSettlePwd;
	}

	public void setVoidSettlePwd(String voidSettlePwd) {
		this.voidSettlePwd = voidSettlePwd;
	}

    public boolean isDiffSaleMidTid() {
        return diffSaleMidTid;
    }

    public void setDiffSaleMidTid(boolean diffSaleMidTid) {
        this.diffSaleMidTid = diffSaleMidTid;
    }

    public boolean isKeyInForPreAuth() {
        return keyInForPreAuth;
    }

    public void setKeyInForPreAuth(boolean keyInForPreAuth) {
        this.keyInForPreAuth = keyInForPreAuth;
    }

    public boolean isKeyInForOffline() {
        return keyInForOffline;
    }

    public void setKeyInForOffline(boolean keyInForOffline) {
        this.keyInForOffline = keyInForOffline;
    }

    public boolean isEnablePinPriority() {
        return enablePinPriority;
    }

    public void setEnablePinPriority(boolean enablePinPriority) {
        this.enablePinPriority = enablePinPriority;
    }

    public boolean isBlockMag() {
        return blockMag;
    }

    public void setBlockMag(boolean blockMag) {
        this.blockMag = blockMag;
    }

    public boolean isCustomerReceipt() {
        return customerReceipt;
    }

    public void setCustomerReceipt(boolean customerReceipt) {
        this.customerReceipt = customerReceipt;
    }

    public void setMidTidSeg(boolean midTidSeg) {
        this.midTidSeg = midTidSeg;
    }

    public boolean isMidTidSeg() {
        return midTidSeg;
    }

    public boolean isEventAutoUpdate() {
        return eventAutoUpdate;
    }

    public void setEventAutoUpdate(boolean eventAutoUpdate) {
        this.eventAutoUpdate = eventAutoUpdate;
    }
}
