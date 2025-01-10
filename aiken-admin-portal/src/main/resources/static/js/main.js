$(document).ready(function () {

    if ($("#mid").length) {
        var val = $('#mid').val().length
        $('#midLength').text(val);
    }
    if ($("#tid").length) {
        var val = $('#tid').val().length
        $('#tidLength').text(val);

    }

    if ($('#userRole').val() == 'ROLE_BANK_USER') {
        $('#userGroup').attr('disabled', false);
    } else {
        $('#userGroup').attr('disabled', true);
    }


    if ($('#eventType').val() == 'ACTIVITY_REPORT') {
        $('#div-report').show();
    } else {
        $('#div-report').hide();
    }

    if ($('#eventType').val() == 'SALE_MID_TID_CHANGE') {
        $('#merchTransType').show();
    } else {
        $('#merchTransType').hide();
    }

    if ($('#eventType').val() == 'CLEAR_BATCH') {
        $('#merchantList').show();
    } else {
        $('#merchantList').hide();
    }

    if ($('#eventType').val() == 'ONE_TIME_SETTING_PASSWORD') {
        $('#onetime-Password').show();
        $('#onetimePassword').attr('required', true);
        $('#onetimePassword').attr('pattern', '\\d{8}');
        $("#onetimePassword").val(generateRandomEightDigitNumber());
        $("#executeType").val('MANUAL').prop('disabled', true);
        $("#executeTypeH").val('MANUAL');
    } else {
        $('#onetime-Password').hide();
        $('#onetimePassword').removeAttr('required');
        $('#onetimePassword').removeAttr('pattern');
        $("#onetimePassword").val(null);
        $("#executeType").prop('disabled', false);
        $("#executeTypeH").prop('disabled', true);
    }

    if ($('#transactionType').val() == 'TODAY_ALL') {
        $('#reportToDate,#reportFromDate').attr('disabled', true);
    } else {
        $('#reportToDate,#reportFromDate').attr('disabled', false);
    }

    if ($('midtidSeg').is(":checked") || (sessionStorage.getItem("midtidSeg") == 'true')) {
        $('#addProfile_diff').show();
        $('#addProfile_norm').hide();
        $('#editProfile_diff').show();
        $('#editProfile_norm').hide();
        $('#addProfile_com_bank').hide();
        $('#editProfile_com').hide();
        console.log("diff");
    } else if (sessionStorage.getItem("bankName") == 'COM_BANK') {
        $('#addProfile_norm').hide();
        $('#addProfile_diff').hide();
        $('#editProfile_norm').hide();
        $('#editProfile_diff').hide();
        $('#addProfile_com_bank').show();
        $('#editProfile_com').show();
        console.log("com");
    } else {
        $('#addProfile_norm').show();
        $('#addProfile_diff').hide();
        $('#editProfile_norm').show();
        $('#editProfile_diff').hide();
        $('#addProfile_com_bank').hide();
        $('#editProfile_com').hide();
        console.log("norm");
    }
    if (sessionStorage.getItem("bankName") == 'COM_BANK') {
        $('#iphoneImeiScan').show();
        console.log("iphoneImeiScan show");
    } else {
        $('#iphoneImeiScan').hide();
        console.log("iphoneImeiScan hide");
    }

    if (($('#type').val() == 'SALE') && (sessionStorage.getItem("diffSaleMidTid") == 'true')) {
        $("#merchantType").attr('disabled', false);
        $('#merchantType').prop('disabled', false);
    } else {
        $("#merchantType").attr('disabled', true);
        $('#merchantType').prop('disabled', true);
    }

    if (($('#currency').val() == 'LKR') && (sessionStorage.getItem("midtidSeg") == 'true') && ($('#type').val() == 'SALE')) {
        console.log("MIDTID Seg Active");
        $('#divMidTidSeg').show();
        $('#divMidTidSeg1').show();


    } else {
        $('#divMidTidSeg').hide();
        $('#divMidTidSeg1').hide();
        console.log("MIDTID Seg inactive");
        $("#onus").attr('disabled', false);

    }

    if (sessionStorage.getItem("midtidSeg") == 'true') {
        console.log("MIDTID Seg Active for profile");
    } else {
        console.log("MIDTID Seg inactive for profile");
    }

    /*	if ($("#keyIn").is(':checked')){
                    $('#key-in-for-amex').show();
                    $("#keyInForAmex").attr('disabled', false);
                    $('#keyInForAmex').prop('checked', false);

                }else{
                    $('#key-in-for-amex').hide();
                    $("#keyInForAmex").attr('disabled', true);
                    $('#keyInForAmex').prop('checked', false);

                }*/

    if ($('#ecr').is(':checked')) {
        console.log("ecr Active");
        $('#ecrSettings').show();

    } else {
        $('#ecrSettings').hide();
        console.log("ecr inactive");
    }

    if (($('#commission').is(':checked')) || (sessionStorage.getItem("commission") == 'true')) {
        console.log("commissionRateFiled show");
        $('#commissionRateFiled').show();
    } else {
        $('#commissionRateFiled').hide();
        $("#commissionRate").val(0.00);
        console.log("commissionRateFiled hide");
    }

    $("#commission").on("click", function () {
        if ($('#commission').is(':checked')) {
            $('#commissionRateFiled').show();

            console.log("commissionRate show");
        } else {
            $('#commissionRateFiled').hide();
            $("#commissionRate").val(0.00);
            console.log("commissionRate hide");
        }
    });

    $("#ecr").on("click", function () {

        if ($("#ecr").is(':checked')) {
            $('#ecrSettings').show();
            $("#cardTypeValidation").attr('disabled', false);
            $('#cardTypeValidation').prop('checked', false);
            $("#saleReceipt").attr('disabled', false);
            $('#saleReceipt').prop('checked', false);

            $("#currencyFromBin").attr('disabled', false);
            $('#currencyFromBin').prop('checked', false);
            $("#currencyFromCard").attr('disabled', false);
            $('#currencyFromCard').prop('checked', false);
            $("#proceedWithLkr").attr('disabled', false);
            $('#proceedWithLkr').prop('checked', true);

            $("#cardTap").attr('disabled', false);
            $('#cardTap').prop('checked', true);
            $("#cardInsert").attr('disabled', false);
            $('#cardInsert').prop('checked', true);
            $("#cardSwipe").attr('disabled', false);
            $('#cardSwipe').prop('checked', true);


        } else {
            $('#ecrSettings').hide();
            $("#cardTypeValidation").attr('disabled', false);
            $('#cardTypeValidation').prop('checked', false);
            $("#saleReceipt").attr('disabled', false);
            $('#saleReceipt').prop('checked', false);

            $("#currencyFromBin").attr('disabled', false);
            $('#currencyFromBin').prop('checked', false);
            $("#currencyFromCard").attr('disabled', false);
            $('#currencyFromCard').prop('checked', false);
            $("#proceedWithLkr").attr('disabled', false);
            $('#proceedWithLkr').prop('checked', false);

            $("#cardTap").attr('disabled', false);
            $('#cardTap').prop('checked', false);
            $("#cardInsert").attr('disabled', false);
            $('#cardInsert').prop('checked', false);
            $("#cardSwipe").attr('disabled', false);
            $('#cardSwipe').prop('checked', false);
        }

        /*$("#noSettle").attr('disabled', !$("#noSettle").attr('disabled'));
        $('#noSettle').prop('checked', false);
        $("#autoSettle").attr('disabled', !$("#autoSettle").attr('disabled'));
        $('#autoSettle').prop('checked', false);
        $("#autoSettleTime").prop("disabled", true);*/
    });

    $("#currencyFromBin").on("click", function () {

        if ($("#currencyFromBin").is(':checked')) {
            $("#currencyFromCard").attr('disabled', false);
            $('#currencyFromCard').prop('checked', false);
            $("#proceedWithLkr").attr('disabled', false);
            $('#proceedWithLkr').prop('checked', false);
        } else {
            $("#currencyFromBin").attr('disabled', false);
            $('#currencyFromBin').prop('checked', false);
            $("#currencyFromCard").attr('disabled', false);
            $('#currencyFromCard').prop('checked', false);
            $("#proceedWithLkr").attr('disabled', false);
            $('#proceedWithLkr').prop('checked', true);
        }
    });

    $("#currencyFromCard").on("click", function () {

        if ($("#currencyFromCard").is(':checked')) {
            $("#currencyFromBin").attr('disabled', false);
            $('#currencyFromBin').prop('checked', false);
            $("#proceedWithLkr").attr('disabled', false);
            $('#proceedWithLkr').prop('checked', false);
        } else {
            $("#currencyFromBin").attr('disabled', false);
            $('#currencyFromBin').prop('checked', false);
            $("#currencyFromCard").attr('disabled', false);
            $('#currencyFromCard').prop('checked', false);
            $("#proceedWithLkr").attr('disabled', false);
            $('#proceedWithLkr').prop('checked', true);
        }
    });

    $("#proceedWithLkr").on("click", function () {
        if ($("#proceedWithLkr").is(':checked')) {
            $("#currencyFromBin").attr('disabled', false);
            $('#currencyFromBin').prop('checked', false);
            $("#currencyFromCard").attr('disabled', false);
            $('#currencyFromCard').prop('checked', false);
        } else {
            $("#currencyFromBin").attr('disabled', false);
            $('#currencyFromBin').prop('checked', false);
            $("#currencyFromCard").attr('disabled', false);
            $('#currencyFromCard').prop('checked', false);
            $("#proceedWithLkr").attr('disabled', false);
            $('#proceedWithLkr').prop('checked', true);
        }
    });


    $("#network2g").on("click", function () {

        if ($("#network2g").is(':checked')) {
            $("#network3g").attr('disabled', false);
            $('#network3g').prop('checked', false);
            $("#network4g").attr('disabled', false);
            $('#network4g').prop('checked', false);
            $("#autoChange").attr('disabled', false);
            $('#autoChange').prop('checked', false);
        } else {
            $("#network2g").attr('disabled', false);
            $('#network2g').prop('checked', false);
            $("#network3g").attr('disabled', false);
            $('#network3g').prop('checked', false);
            $("#network4g").attr('disabled', false);
            $('#network4g').prop('checked', false);
            $("#autoChange").attr('disabled', false);
            $('#autoChange').prop('checked', true);
        }
    });

    $("#network3g").on("click", function () {

        if ($("#network3g").is(':checked')) {
            $("#network2g").attr('disabled', false);
            $('#network2g').prop('checked', false);
            $("#network4g").attr('disabled', false);
            $('#network4g').prop('checked', false);
            $("#autoChange").attr('disabled', false);
            $('#autoChange').prop('checked', false);
        } else {
            $("#network2g").attr('disabled', false);
            $('#network2g').prop('checked', false);
            $("#network3g").attr('disabled', false);
            $('#network3g').prop('checked', false);
            $("#network4g").attr('disabled', false);
            $('#network4g').prop('checked', false);
            $("#autoChange").attr('disabled', false);
            $('#autoChange').prop('checked', true);
        }
    });

    $("#network4g").on("click", function () {
        if ($("#network4g").is(':checked')) {
            $("#network2g").attr('disabled', false);
            $('#network2g').prop('checked', false);
            $("#network3g").attr('disabled', false);
            $('#network3g').prop('checked', false);
            $("#autoChange").attr('disabled', false);
            $('#autoChange').prop('checked', false);
        } else {
            $("#network2g").attr('disabled', false);
            $('#network2g').prop('checked', false);
            $("#network3g").attr('disabled', false);
            $('#network3g').prop('checked', false);
            $("#network4g").attr('disabled', false);
            $('#network4g').prop('checked', false);
            $("#autoChange").attr('disabled', false);
            $('#autoChange').prop('checked', true);
        }
    });

    $("#autoChange").on("click", function () {
        if ($("#autoChange").is(':checked')) {
            $("#network2g").attr('disabled', false);
            $('#network2g').prop('checked', false);
            $("#network3g").attr('disabled', false);
            $('#network3g').prop('checked', false);
            $("#network4g").attr('disabled', false);
            $('#network4g').prop('checked', false);
        } else {
            $("#network2g").attr('disabled', false);
            $('#network2g').prop('checked', false);
            $("#network3g").attr('disabled', false);
            $('#network3g').prop('checked', false);
            $("#network4g").attr('disabled', false);
            $('#network4g').prop('checked', false);
            $("#autoChange").attr('disabled', false);
            $('#autoChange').prop('checked', true);
        }
    });

    $("#autoSettle").on("click", function () {
        $("#forceSettle").attr('disabled', !$("#forceSettle").attr('disabled'));
        $('#forceSettle').prop('checked', false);
        $("#noSettle").attr('disabled', !$("#noSettle").attr('disabled'));
        $('#noSettle').prop('checked', false);
        //$("#autoSettleTime").attr('enabled', !$("#autoSettleTime").attr('enabled'));
        //$('#autoSettleTime').prop('enabled', false);
        $("#autoSettleTime").prop("disabled", !$(this).is(':checked'));
    });

    $("#forceSettle").on("click", function () {
        $("#noSettle").attr('disabled', !$("#noSettle").attr('disabled'));
        $('#noSettle').prop('checked', false);
        $("#autoSettle").attr('disabled', !$("#autoSettle").attr('disabled'));
        $('#autoSettle').prop('checked', false);
        $("#autoSettleTime").prop("disabled", true);
    });

    $("#noSettle").on("click", function () {
        $("#forceSettle").attr('disabled', !$("#forceSettle").attr('disabled'));
        $('#forceSettle').prop('checked', false);
        $("#autoSettle").attr('disabled', !$("#autoSettle").attr('disabled'));
        $('#autoSettle').prop('checked', false);
        $("#autoSettleTime").prop("disabled", true);
    });

    $("#dcc").on("click", function () {

        /*if ($("#dcc").is(':checked')){
            $("#offline").attr('disabled', true);
            $('#offline').prop('checked', false);
            $("#preAuth").attr('disabled', true);
            $('#preAuth').prop('checked', false);
        }else{
            $("#offline").attr('disabled', false);
            $('#offline').prop('checked', false);
            $("#preAuth").attr('disabled', false);
            $('#preAuth').prop('checked', false);
        }*/
    });

    $("#onus").on("click", function () {

        if ($("#onus").is(':checked')) {
            //$("#offus").attr('disabled', false);
            $('#offus').prop('checked', false);
        }
    });
    $("#offus").on("click", function () {

        if ($("#offus").is(':checked')) {
            //$("#offus").attr('disabled', false);
            $('#onus').prop('checked', false);
        }
    });

    $("#foreignMer").on("click", function () {
        if ($("#foreignMer").is(':checked')) {
            $("#onus").attr('disabled', true);
            $('#onus').prop('checked', false);
            $("#offus").attr('disabled', true);
            $('#offus').prop('checked', false);
            $("#localMer").attr('disabled', false);
            $('#localMer').prop('checked', false);
            $("#dcc").attr('disabled', false);
            $('#dcc').prop('checked', false);
            $("#preAuth").attr('disabled', false);
            $('#preAuth').prop('checked', false);
            $("#jcb").attr('disabled', false);
            $('#jcb').prop('checked', false);
        } else {
            $("#onus").attr('disabled', true);
            $('#onus').prop('checked', false);
            $("#offus").attr('disabled', true);
            $('#offus').prop('checked', false);
            $("#foreignMer").attr('disabled', false);
            $('#foreignMer').prop('checked', true);
            $("#localMer").attr('disabled', false);
            $('#localMer').prop('checked', false);

        }
    });

    $("#localMer").on("click", function () {
        if ($("#localMer").is(':checked')) {
            $("#onus").attr('disabled', false);
            $('#onus').prop('checked', false);
            $("#offus").attr('disabled', false);
            $('#offus').prop('checked', false);
            $("#foreignMer").attr('disabled', false);
            $('#foreignMer').prop('checked', false);
            $("#dcc").attr('disabled', true);
            $('#dcc').prop('checked', false);
            $("#preAuth").attr('disabled', true);
            $('#preAuth').prop('checked', false);
            $("#jcb").attr('disabled', true);
            $('#jcb').prop('checked', false);

        } else {
            $("#onus").attr('disabled', true);
            $('#onus').prop('checked', false);
            $("#offus").attr('disabled', true);
            $('#offus').prop('checked', false);
            $("#localMer").attr('disabled', false);
            $('#localMer').prop('checked', false);
            $("#foreignMer").attr('disabled', false);
            $('#foreignMer').prop('checked', true);
            $("#dcc").attr('disabled', false);
            $('#dcc').prop('checked', false);
            $("#preAuth").attr('disabled', false);
            $('#preAuth').prop('checked', false);
            $("#jcb").attr('disabled', false);
            $('#jcb').prop('checked', false);
        }
    });

    $("#onus").on("click", function () {
        if ($("#onus").is(':checked')) {
            $("#localMer").attr('disabled', false);
            $('#localMer').prop('checked', true);
            $("#offus").attr('disabled', false);
            $('#offus').prop('checked', false);
            $("#foreignMer").attr('disabled', false);
            $('#foreignMer').prop('checked', false);
            $("#dcc").attr('disabled', true);
            $('#dcc').prop('checked', false);
            $("#preAuth").attr('disabled', true);
            $('#preAuth').prop('checked', false);
            $("#jcb").attr('disabled', true);
            $('#jcb').prop('checked', false);
        } else {
            $("#onus").attr('disabled', true);
            $('#onus').prop('checked', false);
            $("#offus").attr('disabled', true);
            $('#offus').prop('checked', false);
            $("#localMer").attr('disabled', false);
            $('#localMer').prop('checked', false);
            $("#foreignMer").attr('disabled', false);
            $('#foreignMer').prop('checked', true);
            $("#dcc").attr('disabled', false);
            $('#dcc').prop('checked', false);
            $("#preAuth").attr('disabled', false);
            $('#preAuth').prop('checked', false);
            $("#jcb").attr('disabled', false);
            $('#jcb').prop('checked', false);
        }
    });
    $("#offus").on("click", function () {
        if ($("#offus").is(':checked')) {
            $("#localMer").attr('disabled', false);
            $('#localMer').prop('checked', true);
            $("#onus").attr('disabled', false);
            $('#onus').prop('checked', false);
            $("#foreignMer").attr('disabled', false);
            $('#foreignMer').prop('checked', false);
            $("#dcc").attr('disabled', true);
            $('#dcc').prop('checked', false);
            $("#preAuth").attr('disabled', true);
            $('#preAuth').prop('checked', false);
            $("#jcb").attr('disabled', true);
            $('#jcb').prop('checked', false);
        } else {
            $("#onus").attr('disabled', true);
            $('#onus').prop('checked', false);
            $("#offus").attr('disabled', true);
            $('#offus').prop('checked', false);
            $("#localMer").attr('disabled', false);
            $('#localMer').prop('checked', false);
            $("#foreignMer").attr('disabled', false);
            $('#foreignMer').prop('checked', true);
            $("#dcc").attr('disabled', false);
            $('#dcc').prop('checked', false);
            $("#preAuth").attr('disabled', false);
            $('#preAuth').prop('checked', false);
            $("#jcb").attr('disabled', false);
            $('#jcb').prop('checked', false);
        }
    });

    if ($('#localMer').is(":checked")) {
        $("#dcc").attr('disabled', true);
        $('#dcc').prop('checked', false);
        $("#preAuth").attr('disabled', true);
        $('#preAuth').prop('checked', false);

    }

    $("#merchantPortal").on("click", function () {
        if ($("#merchantPortal").is(':checked')) {
            $("#clientCredentials").attr('disabled', false);
            $('#clientCredentials').prop('checked', true);
        }
        else {
            $("#clientCredentials").attr('disabled', false);
            $('#clientCredentials').prop('checked', false);
        }
    });

    $("#dcc").on("click", function () {
        if ($("#dcc").is(':checked')) {
            $("#foreignMer").attr('disabled', false);
            $('#foreignMer').prop('checked', true);
            $("#jcb").attr('disabled', true);
            $('#jcb').prop('checked', false);
        } else {
            $("#jcb").attr('disabled', false);
            $('#jcb').prop('checked', false);
        }
    });
    if ($("#dcc").is(':checked')) {
        $("#foreignMer").attr('disabled', false);
        $('#foreignMer').prop('checked', true);
        $("#jcb").attr('disabled', true);
        $('#jcb').prop('checked', false);
    }
    //      else{
    //          $("#jcb").attr('disabled', false);
    //          $('#jcb').prop('checked', false);
    //      }


    $('#mid').on('input', function (e) {
        var val = $('#mid').val().length
        $('#midLength').text(val);
    });
    $('#tid').on('input', function (e) {
        var val = $('#tid').val().length
        $('#tidLength').text(val);
    });

    $("#offline").on("click", function () {
        /*if ($("#offline").is(':checked')){
            $('#dcc').prop('checked', false);
        }*/
    });
    $("#preAuth").on("click", function () {
        /*if ($("#preAuth").is(':checked')){
            $('#dcc').prop('checked', false);
        }*/
    });

    /*$("#keyIn").on("click", function() {

            if ($("#keyIn").is(':checked')){
                $('#key-in-for-amex').show();
                $("#keyInForAmex").attr('disabled', false);
                $('#keyInForAmex').prop('checked', false);

            }else{
                $('#key-in-for-amex').hide();
                $("#keyInForAmex").attr('disabled', true);
                $('#keyInForAmex').prop('checked', false);

            }
    });
    $("#keyInForAmex").on("click", function() {

            if ($("#keyInForAmex").is(':checked')){
                $('#keyIn').prop('checked', true);
            }
    });*/

    $("#modifyDevice,#saveDevice").submit(function (e) {
        $("#btnModifyDevice").attr("disabled", true);
        $("#btnSaveDevice").attr("disabled", true);
        sessionStorage.setItem("isButtonDisabled", true);
        return true;
    });

    $("#type").on("change", function () {
        var val = $('#type').val()
        if (val == 'SALE') {
            $('#div-qr').hide();
            $('#div-solo').hide();
            $('#div-amex').hide();
        } else if (val == 'QR') {
            $('#div-qr').show();
            if (sessionStorage.getItem("bankName") == 'HNB') {
                $('#div-solo').show();
            } else
                $('#div-solo').hide();
            $('#div-amex').hide();
        } else if (val == 'AMEX') {
            $('#div-amex').show();
            $('#div-qr').hide();
            $('#div-solo').hide();
        } else {
            $('#div-qr').hide();
            $('#div-amex').hide();
            $('#div-solo').hide();
        }
    });

    $("#currency").on("change", function () {
        var val = $('#currency').val()
        if (val == 'LKR') {
            $('#divMidTidSeg1').show();
        }
        else {
            $('#divMidTidSeg1').hide();

        }
    });

    $("#eventType").on("change", function () {
        var val = $('#eventType').val()
        console.log(val);
        if (val == 'SALE_MID_TID_CHANGE') {
            $('#merchTransType').show();
        } else {
            $('#merchTransType').hide();
        }
        if (val == 'CLEAR_BATCH') {
            $('#merchantList').show();
        } else {
            $('#merchantList').hide();
        }
        if (val == 'ONE_TIME_SETTING_PASSWORD') {
            $('#onetime-Password').show();
            $('#onetimePassword').attr('required', true);
            $('#onetimePassword').attr('pattern', '\\d{8}');
            $("#onetimePassword").val(generateRandomEightDigitNumber());
            $("#executeType").val('MANUAL');
            $("#executeType").prop('disabled', true);
            $("#executeTypeH").prop('disabled', false);
            $("#executeTypeH").val('MANUAL');

        } else {
            $('#onetime-Password').hide();
            $('#onetimePassword').removeAttr('required');
            $('#onetimePassword').removeAttr('pattern');
            $("#onetimePassword").val(null);
            $("#executeType").prop('disabled', false);
            $("#executeTypeH").prop('disabled', true);
        }
    });

    $(function () {
        var val = $('#eventType').val()

        if (val == 'ACTIVITY_REPORT') {
            $('#div-report').show();
        } else {
            $('#div-report').hide();
        }

        if (val == 'SALE_MID_TID_CHANGE') {
            $('#merchTransType').show();
        } else {
            $('#merchTransType').hide();
        }

        if (val == 'CLEAR_BATCH') {
            $('#merchantList').show();
        } else {
            $('#merchantList').hide();
        }

        if (val == 'ONE_TIME_SETTING_PASSWORD') {
            $('#onetime-Password').show();
            $('#onetimePassword').attr('required', true);
            $('#onetimePassword').attr('pattern', '\\d{8}');
            $("#onetimePassword").val(generateRandomEightDigitNumber());
            $("#executeType").val('MANUAL').prop('disabled', true);
            $("#executeTypeH").val('MANUAL');

        } else {
            $('#onetime-Password').hide();
            $('#onetimePassword').removeAttr('required');
            $('#onetimePassword').removeAttr('pattern');
            $("#onetimePassword").val(null);
            $("#executeType").prop('disabled', false);
            $("#executeTypeH").prop('disabled', true);
        }
    });

// Function to generate a random 8-digit number (with leading zeros)
function generateRandomEightDigitNumber() {

    var onetimePassword = $('#onetimePassword').val();

    if (!onetimePassword) {
        // Generate a random number between 0 and 99999999
        let randomNum = Math.floor(Math.random() * 100000000);
        let generatedPassword = String(randomNum).padStart(8, '0');
        $('#onetimePassword').val(generatedPassword);
        return generatedPassword;

    } else {
        return onetimePassword;
    }

}

    $("#bankName").on("change", function () {
        var val = $('#bankName').val()
        console.log(val);
        if (val == 'COM_BANK') {
            $("#pushNotification").prop('checked', true);
        } else {
            $("#pushNotification").prop('checked', false);
        }
    });

    $("#midtidSeg").on("change", function () {
        if ($('#midtidSeg').is(":checked") || (sessionStorage.getItem("midtidSeg") == 'true') || $('#midtidSeg').is(":disabled")) {
            $('#addProfile_diff').show();
            $('#addProfile_norm').hide();
            $('#editProfile_diff').show();
            $('#editProfile_norm').hide();
            $('#addProfile_com_bank').hide();
            $('#editProfile_com').hide();
            console.log("addProfile_diff1");
        } else {
            $('#addProfile_norm').show();
            $('#addProfile_diff').hide();
            $('#editProfile_norm').show();
            $('#editProfile_diff').hide();
            $('#addProfile_com_bank').hide();
            $('#editProfile_com').hide();
            console.log("addProfile_norm1");
        }
    });

    if ((sessionStorage.getItem("midtidSeg") == 'true') || $('#midtidSeg').is(":checked")) {
        $('#addProfile_diff').show();
        $('#addProfile_norm').hide();
        $('#editProfile_diff').show();
        $('#editProfile_norm').hide();
        $('#editProfile_com').hide();
        console.log("addProfile_diff2");
    } else if (sessionStorage.getItem("bankName") == 'COM_BANK' || $('#bankName').val() == 'COM_BANK') {
        $('#addProfile_norm').hide();
        $('#addProfile_diff').hide();
        $('#editProfile_norm').hide();
        $('#editProfile_diff').hide();
        $('#addProfile_com_bank').show();
        $('#editProfile_com').show();
        console.log("com bank ");
    } else {
        $('#addProfile_norm').show();
        $('#addProfile_diff').hide();
        $('#editProfile_norm').show();
        $('#editProfile_diff').hide();
        $('#editProfile_com').hide();
        console.log("addProfile_norm2");
    }



    //
    //if (($('#currency').val()=='LKR') && (sessionStorage.getItem("midtidSeg")=='true')){
    $("#currency,#type").on("change", function () {
        if (($('#midtidSeg').is(":checked") || (sessionStorage.getItem("midtidSeg") == 'true')) && ($('#currency').val() == 'LKR') && ($('#type').val() == 'SALE')) {
            $('#divMidTidSeg').show();
            $('#divMidTidSeg1').show();
            console.log("mid tid setting show");
            $("#onus").attr('disabled', true);
            $('#onus').prop('checked', false);
            $("#offus").attr('disabled', true);
            $('#offus').prop('checked', false);
            $("#foreignMer").attr('disabled', false);
            $('#foreignMer').prop('checked', true);
            $("#localMer").attr('disabled', false);
            $('#localMer').prop('checked', false);
        } else {
            $('#divMidTidSeg').hide();
            $('#divMidTidSeg1').hide();
            $("#onus").attr('disabled', false);
            $('#onus').prop('checked', false);
            $("#offus").attr('disabled', false);
            $('#offus').prop('checked', false);
            $("#foreignMer").attr('disabled', false);
            $('#foreignMer').prop('checked', false);
            $("#localMer").attr('disabled', false);
            $('#localMer').prop('checked', false);
        }
    });
    //            $("#currency").on("change", function() {
    //                    if ( ($('#currency').val()=='LKR') ){
    //                                    $('#divMidTidSeg1').show();
    //                                    $("#onus").attr('disabled', true);
    //                                    $('#onus').prop('checked', false);
    //                                    $("#offus").attr('disabled', true);
    //                                    $('#offus').prop('checked', false);
    //                                    $("#foreignMer").attr('disabled', false);
    //                                    $('#foreignMer').prop('checked', true);
    //                                    $("#localMer").attr('disabled', false);
    //                                    $('#localMer').prop('checked', false);
    //                                }else {
    //                                    $('#divMidTidSeg1').hide();
    //                                    $("#onus").attr('disabled', false);
    //                                    $('#onus').prop('checked', false);
    //                                    $("#offus").attr('disabled', false);
    //                                    $('#offus').prop('checked', false);
    //                                    $("#foreignMer").attr('disabled', false);
    //                                    $('#foreignMer').prop('checked', false);
    //                                    $("#localMer").attr('disabled', false);
    //                                    $('#localMer').prop('checked', false);
    //
    //                                }
    //                });


    //Bin Rules
    $("#ruleType").on("change", function () {
        var val = $('#ruleType').val()
        if (val == 'SINGLE') {
            //$('#binUpto').hide();
            $("#binEndFrom").val("");
            $("#binEndFrom").prop("disabled", true);
            $("#binStartFrom").prop("disabled", false);

        } else if (val == 'MULTIPLE') {
            //$('#binUpto').show();
            $("#binEndFrom").prop("disabled", false);
            $("#binStartFrom").prop("disabled", false);
        } else if (val == 'FULL_RANGE') {
            //$('#binUpto').show();
            $("#binEndFrom").prop("disabled", true);
            $("#binEndFrom").val("");
            $("#binStartFrom").val("");
            $("#binStartFrom").prop("disabled", true);
        } else {
            $("#binEndFrom").prop("disabled", false);
            $("#binStartFrom").prop("disabled", false);
        }
    });

    $("#binEndFrom").on("change", function () {
        var val = $('#ruleType').val()
        if (val == 'MULTIPLE') {
            console.log("#ruleType MULTIPLE");
            $("#binEndFrom").prop("disabled", false);
            $("#binStartFrom").prop("disabled", false);

        } else if (val == 'SINGLE') {
            $("#binEndFrom").prop("disabled", true);
            $("#binStartFrom").prop("disabled", false);
            $("#binEndFrom").val("");
            console.log("#ruleType SINGLE");

        } else if (val == 'FULL_RANGE') {
            $("#binEndFrom").prop("disabled", true);
            $("#binStartFrom").prop("disabled", true);
            $("#binEndFrom").val("");
            $("#binStartFrom").val("");
            console.log("#ruleType FULL_RANGE");

        } else {
            $("#binEndFrom").prop("disabled", false);
            $("#binStartFrom").prop("disabled", false);
            console.log("#ruleType Other");
        }
    });

    $("#type").on("change", function () {
        var val = $('#type').val()
        console.log("#Type SALE: " + sessionStorage.getItem("diffSaleMidTid"));
        if ((val == 'SALE') && (sessionStorage.getItem("diffSaleMidTid") == 'true')) {
            console.log("#Type SALE");
            $("#merchantType").attr('disabled', false);
            $('#merchantType').prop('disabled', false);
        } else {
            $("#merchantType").attr("disabled", true);
            $("#merchantType").prop("disabled", true);
            console.log("#Type Other");
        }
    });

    /*	if ($('#ruleType').val()=='MULTIPLE'){
            console.log("#ruleType MULTIPLE");
            $('#binUpto').show();

        }else {
            $('#binUpto').hide();
            console.log("#ruleType SINGLE");
        }*/

    if ($('#type').val() == 'SALE') {
        $('#div-qr').hide();
        $('#div-solo').hide();
        $('#div-amex').hide();
    } else if ($('#type').val() == 'QR') {
        $('#div-qr').show();
        if (sessionStorage.getItem("bankName") == 'HNB') {
            $('#div-solo').show();
        } else
            $('#div-solo').hide();
        $('#div-amex').hide();
    } else if ($('#type').val() == 'AMEX') {
        $('#div-amex').show();
        $('#div-qr').hide();
        $('#div-solo').hide();
    } else {
        $('#div-qr').hide();
        $('#div-amex').hide();
        $('#div-solo').hide();
    }

    //sessionStorage.removeItem(keyname)
    $("#addMerchant").on("click", function () {
        console.log("Button clicked addToCart");
        console.log("Remove Merchant Data");
        sessionStorage.removeItem("type");
        sessionStorage.removeItem("month");
        sessionStorage.removeItem("currency");
        sessionStorage.removeItem("description");
        sessionStorage.removeItem("minAmount");
        sessionStorage.removeItem("maxAmount");
        sessionStorage.removeItem("mid");
        sessionStorage.removeItem("tid");
        sessionStorage.removeItem("preAuth");
        sessionStorage.removeItem("dcc");
        sessionStorage.removeItem("offline");
        sessionStorage.removeItem("jcb");
        sessionStorage.removeItem("merchantUserId");
        sessionStorage.removeItem("merchantPassword");
        sessionStorage.removeItem("checksumKey");
        sessionStorage.removeItem("vid");
        sessionStorage.removeItem("cid");
        sessionStorage.removeItem("localMer");
        sessionStorage.removeItem("foreignMer");
        sessionStorage.removeItem("onus");
        sessionStorage.removeItem("offus");
        sessionStorage.setItem("midtidSeg", $('#midtidSeg').is(":checked"));
        sessionStorage.setItem("bankName", $('#bankName').val());

        $("#foreignMer").attr('disabled', false);
        $('#foreignMer').prop('checked', true);

    });

    $("#addToCart").on("click", function () {
        console.log("Button clicked addMerchant");
        console.log("add to cart");
        sessionStorage.removeItem("type");
        sessionStorage.removeItem("month");
        sessionStorage.removeItem("currency");
        sessionStorage.removeItem("description");
        sessionStorage.removeItem("minAmount");
        sessionStorage.removeItem("maxAmount");
        sessionStorage.removeItem("mid");
        sessionStorage.removeItem("tid");
        sessionStorage.removeItem("preAuth");
        sessionStorage.removeItem("dcc");
        sessionStorage.removeItem("jcb");
        sessionStorage.removeItem("offline");
        sessionStorage.removeItem("merchantUserId");
        sessionStorage.removeItem("merchantPassword");
        sessionStorage.removeItem("checksumKey");
        sessionStorage.removeItem("vid");
        sessionStorage.removeItem("cid");
        sessionStorage.removeItem("localMer");
        sessionStorage.removeItem("foreignMer");
        sessionStorage.removeItem("onus");
        sessionStorage.removeItem("offus");

    });


    $("#btn_new_rule").on("click", function () {
        sessionStorage.setItem("type", $('#type').val());
        sessionStorage.setItem("month", $('#month').val());
        sessionStorage.setItem("currency", $('#currency').val());
        sessionStorage.setItem("description", $('#description').val());
        sessionStorage.setItem("minAmount", $('#minAmount').val());
        sessionStorage.setItem("maxAmount", $('#maxAmount').val());
        sessionStorage.setItem("mid", $('#mid').val());
        sessionStorage.setItem("tid", $('#tid').val());
        sessionStorage.setItem("preAuth", $('#preAuth').is(":checked"));
        sessionStorage.setItem("dcc", $('#dcc').is(":checked"));
        sessionStorage.setItem("offline", $('#offline').is(":checked"));
        sessionStorage.setItem("jcb", $('#jcb').is(":checked"));
        sessionStorage.setItem("merchantUserId", $('#merchantUserId').val());
        sessionStorage.setItem("merchantPassword", $('#merchantPassword').val());
        sessionStorage.setItem("checksumKey", $('#checksumKey').val());
        sessionStorage.setItem("vid", $('#vid').val());
        sessionStorage.setItem("cid", $('#cid').val());
        sessionStorage.setItem("localMer", $('#localMer').is(":checked"));
        sessionStorage.setItem("foreignMer", $('#foreignMer').is(":checked"));
        sessionStorage.setItem("onus", $('#onus').is(":checked"));
        sessionStorage.setItem("offus", $('#offus').is(":checked"));

    });


    /*	$("#addProfile").on("click", function() {
            clearProfileParams();
            alert("addProfile clicked");
        });*/


    $(function () {
        var tittle = $(document).find("title").text();
        if ((tittle == 'Add Merchant') || (tittle == 'Modify Merchant') || (tittle == 'Add New Merchant')) {

            if (sessionStorage.getItem("type") == null || sessionStorage.getItem("type") === 'undefined') {

                // variable is undefined or null
            } else {
                if (sessionStorage.getItem("type").length > 0) {
                    $('#type').val(sessionStorage.getItem("type"));
                    $('#month').val(sessionStorage.getItem("month"));
                    $('#currency').val(sessionStorage.getItem("currency"));
                    $('#description').val(sessionStorage.getItem("description"));
                    $('#minAmount').val(sessionStorage.getItem("minAmount"));
                    $('#maxAmount').val(sessionStorage.getItem("maxAmount"));
                    $('#mid').val(sessionStorage.getItem("mid"));
                    $('#tid').val(sessionStorage.getItem("tid"));
                    $("#preAuth").prop('checked', (sessionStorage.getItem("preAuth") == 'true') ? true : false);
                    $("#dcc").prop('checked', (sessionStorage.getItem("dcc") == 'true') ? true : false);
                    $("#jcb").prop('checked', (sessionStorage.getItem("jcb") == 'true') ? true : false);
                    $("#offline").prop('checked', (sessionStorage.getItem("offline") == 'true') ? true : false);

                    $('#merchantUserId').val(sessionStorage.getItem("merchantUserId"));
                    $('#merchantPassword').val(sessionStorage.getItem("merchantPassword"));
                    $('#checksumKey').val(sessionStorage.getItem("checksumKey"));
                    $('#vid').val(sessionStorage.getItem("vid"));
                    $('#cid').val(sessionStorage.getItem("cid"));

                    $("#localMer").prop('checked', (sessionStorage.getItem("localMer") == 'true') ? true : false);
                    $("#foreignMer").prop('checked', (sessionStorage.getItem("foreignMer") == 'true') ? true : false);
                    $("#onus").prop('checked', (sessionStorage.getItem("onus") == 'true') ? true : false);
                    $("#offus").prop('checked', (sessionStorage.getItem("offus") == 'true') ? true : false);
                    // Temp
                    var val = $('#type').val()
                    console.log("#Merchant SALE: " + sessionStorage.getItem("diffSaleMidTid"));
                    if ((val == 'SALE') && (sessionStorage.getItem("diffSaleMidTid") == 'true')) {
                        console.log("#Merchant SALE");
                        $("#merchantType").attr('disabled', false);
                        $('#merchantType').prop('disabled', false);
                    } else {
                        $("#merchantType").prop("disabled", true);
                        $("#merchantType").prop("disabled", true);
                        console.log("#Merchant Other");

                    }
                }
            }
        }
    });

    $("#transactionType").on("change", function () {
        var val = $('#transactionType').val()
        if (val == 'TODAY_ALL') {
            dt = new Date();
            var tDay = (dt.getFullYear() + "-" + zeroPadded(dt.getMonth() + 1) + "-" + zeroPadded(dt.getDate()));
            $("#reportToDate,#reportFromDate").val(tDay);
            $('#reportToDate,#reportFromDate').attr('disabled', true);
        } else {
            $('#reportToDate,#reportFromDate').attr('disabled', false);
        }
    });


    $("#eventType").on("change", function () {
        var val = $('#eventType').val()
        if (val == 'ACTIVITY_REPORT') {
            $('#div-report').show();
        } else {
            $('#div-report').hide();
        }
    });

    $("#userRole").on("change", function () {
        if ($('#userRole').val() == 'ROLE_BANK_USER') {
            $('#userGroup').attr('disabled', false);
        } else {
            $('#userGroup').attr('disabled', true);
        }
    });


    $("#addMerchant,#btnEditDevice,#addOfflineUse,#btnDeleteMerchant,#btnModifyMerchant").click(function () {
        sessionStorage.clear;
        sessionStorage.setItem("bankName", $('#bankName').val());
        if (($('#serialNo').val().length != '') || ($('#serialNo').val().val().length != 0)) {
            sessionStorage.setItem("bankName", $('#bankName').val());
            sessionStorage.setItem("serialNo", $('#serialNo').val());
            sessionStorage.setItem("merchantName", $('#merchantName').val());
            sessionStorage.setItem("merchantAddress", $('#merchantAddress').val());
            sessionStorage.setItem("noSettle", $('#noSettle').is(":checked"));
            sessionStorage.setItem("mkiPreAuth", $('#mkiPreAuth').is(":checked"));
            sessionStorage.setItem("mkiOffline", $('#mkiOffline').is(":checked"));
            sessionStorage.setItem("forceSettle", $('#forceSettle').is(":checked"));
            sessionStorage.setItem("autoSettle", $('#autoSettle').is(":checked"));
            sessionStorage.setItem("lkrDefaultCurr1", $('#lkrDefaultCurr1').is(":checked"));
            sessionStorage.setItem("signPriority", $('#signPriority').is(":checked"));
            sessionStorage.setItem("blockMag", $('#blockMag').is(":checked"));
            sessionStorage.setItem("customerReceipt", $('#customerReceipt').is(":checked"));
            sessionStorage.setItem("autoSettleTime", $('#autoSettleTime').val());
            sessionStorage.setItem("ecr", $('#ecr').is(":checked"));
            sessionStorage.setItem("ecrQr", $('#ecrQr').is(":checked"));
            sessionStorage.setItem("signature", $('#signature').is(":checked"));
            sessionStorage.setItem("debugMode", $('#debugMode').is(":checked"));
            sessionStorage.setItem("keyIn", $('#keyIn').is(":checked"));
            sessionStorage.setItem("activityTracker", $('#activityTracker').is(":checked"));
            sessionStorage.setItem("qrRefund", $('#qrRefund').is(":checked"));
            sessionStorage.setItem("custContactNo", $('#custContactNo').val());
            sessionStorage.setItem("simNo", $('#simNo').val());
            sessionStorage.setItem("mobileNo", $('#mobileNo').val());
            sessionStorage.setItem("remark", $('#remark').val());
            sessionStorage.setItem("reversalHistory", $('#reversalHistory').is(":checked"));
            sessionStorage.setItem("enableAmex", $('#enableAmex').is(":checked"));
            sessionStorage.setItem("pushNotification", $('#pushNotification').is(":checked"));
            sessionStorage.setItem("preAuth", $('#preAuth').is(":checked"));
            sessionStorage.setItem("keyInForAmex", $('#keyInForAmex').is(":checked"));
            sessionStorage.setItem("popupMessage", $('#popupMessage').is(":checked"));

            sessionStorage.setItem("autoReversal", $('#autoReversal').is(":checked"));
            sessionStorage.setItem("printless", $('#printless').is(":checked"));

            sessionStorage.setItem("cardTypeValidation", $('#cardTypeValidation').is(":checked"));
            sessionStorage.setItem("saleReceipt", $('#saleReceipt').is(":checked"));
            sessionStorage.setItem("dccPayload", $('#dccPayload').is(":checked"));

            sessionStorage.setItem("currencyFromBin", $('#currencyFromBin').is(":checked"));
            sessionStorage.setItem("currencyFromCard", $('#currencyFromCard').is(":checked"));
            sessionStorage.setItem("proceedWithLkr", $('#proceedWithLkr').is(":checked"));

            sessionStorage.setItem("cardTap", $('#cardTap').is(":checked"));
            sessionStorage.setItem("cardInsert", $('#cardInsert').is(":checked"));
            sessionStorage.setItem("cardSwipe", $('#cardSwipe').is(":checked"));

            sessionStorage.setItem("network2g", $('#network2g').is(":checked"));
            sessionStorage.setItem("network3g", $('#network3g').is(":checked"));
            sessionStorage.setItem("network4g", $('#network4g').is(":checked"));

            sessionStorage.setItem("merchantPortal", $('#merchantPortal').is(":checked"));
            sessionStorage.setItem("resendVoid", $('#resendVoid').is(":checked"));
            sessionStorage.setItem("clientCredentials", $('#clientCredentials').is(":checked"));
            sessionStorage.setItem("printReceipt", $('#printReceipt').is(":checked"));
            sessionStorage.setItem("keyInAmex", $('#keyInAmex').is(":checked"));
            sessionStorage.setItem("autoChange", $('#autoChange').is(":checked"));

            sessionStorage.setItem("voidPwd", $('#voidPwd').val());

            sessionStorage.setItem("diffSaleMidTid", $('#diffSaleMidTid').is(":checked"));

            sessionStorage.setItem("midtidSeg", $('#midtidSeg').is(":checked"));
            sessionStorage.setItem("eventAutoUpdate", $('#eventAutoUpdate').is(":checked"));
            sessionStorage.setItem("newVoidPwd", $('#newVoidPwd').val());
            sessionStorage.setItem("newSettlementPwd", $('#newSettlementPwd').val());
            sessionStorage.setItem("imeiScan", $('#imeiScan').is(":checked"));
            sessionStorage.setItem("commission", $('#commission').is(":checked"));
            sessionStorage.setItem("commissionRate", $('#commissionRate').val());
            sessionStorage.setItem("refNumberEnable", $('#refNumberEnable').is(":checked"));
            sessionStorage.setItem("tleProfilesEnable", $('#tleProfilesEnable').is(":checked"));

            // Need to add new fields in reg device page
        }
    });


    $("#addProfToCart").click(function () {
        console.log("Data Clear in session");

        sessionStorage.removeItem("profileName");
        sessionStorage.removeItem("profileMerchantName");
        sessionStorage.removeItem("profileMerchantAddress");

        sessionStorage.removeItem("vContact");
        sessionStorage.removeItem("vConless");
        sessionStorage.removeItem("visaNoCvm");
        sessionStorage.removeItem("visaTransLimit");

        sessionStorage.removeItem("mcContact");
        sessionStorage.removeItem("mcConless");
        sessionStorage.removeItem("mcNoCvm");
        sessionStorage.removeItem("mcTransLimit");

        sessionStorage.removeItem("amexContact");
        sessionStorage.removeItem("amexConless");
        sessionStorage.removeItem("amexNoCvm");
        sessionStorage.removeItem("amexTransLimit");

        sessionStorage.removeItem("upayContact");
        sessionStorage.removeItem("upayConless");
        sessionStorage.removeItem("upayNoCvm");
        sessionStorage.removeItem("upayTransLimit");

        sessionStorage.removeItem("jcbContact");
        sessionStorage.removeItem("jcbConless");
        sessionStorage.removeItem("jcbNoCvm");
        sessionStorage.removeItem("jcbTransLimit");
        sessionStorage.removeItem("customerCopy");
        sessionStorage.removeItem("tlsEnable");

    });
    $("#addProfile,#addProfile_diff_sale,#addProfile_com_bank,#addProfile_diff_sale_modify,#addProfile_modify,#addProfile_com,#addOfflineUser,#btnEditProf,#btnDeleteProf").click(function () {

        console.log("Data Storing in session");
        if (($('#serialNo').val().length != '') || ($('#serialNo').val().val().length != 0)) {
            sessionStorage.setItem("bankName", $('#bankName').val());
            sessionStorage.setItem("serialNo", $('#serialNo').val());
            sessionStorage.setItem("merchantName", $('#merchantName').val());
            sessionStorage.setItem("merchantAddress", $('#merchantAddress').val());
            sessionStorage.setItem("noSettle", $('#noSettle').is(":checked"));
            sessionStorage.setItem("forceSettle", $('#forceSettle').is(":checked"));
            sessionStorage.setItem("autoSettle", $('#autoSettle').is(":checked"));
            sessionStorage.setItem("mkiPreAuth", $('#mkiPreAuth').is(":checked"));
            sessionStorage.setItem("mkiOffline", $('#mkiOffline').is(":checked"));
            sessionStorage.setItem("autoSettleTime", $('#autoSettleTime').val());
            sessionStorage.setItem("ecr", $('#ecr').is(":checked"));
            sessionStorage.setItem("ecrQr", $('#ecrQr').is(":checked"));
            sessionStorage.setItem("signature", $('#signature').is(":checked"));
            sessionStorage.setItem("debugMode", $('#debugMode').is(":checked"));
            sessionStorage.setItem("keyIn", $('#keyIn').is(":checked"));
            sessionStorage.setItem("lkrDefaultCurr1", $('#lkrDefaultCurr1').is(":checked"));
            sessionStorage.setItem("signPriority", $('#signPriority').is(":checked"));
            sessionStorage.setItem("blockMag", $('#blockMag').is(":checked"));
            sessionStorage.setItem("customerReceipt", $('#customerReceipt').is(":checked"));
            sessionStorage.setItem("activityTracker", $('#activityTracker').is(":checked"));
            sessionStorage.setItem("qrRefund", $('#qrRefund').is(":checked"));
            sessionStorage.setItem("custContactNo", $('#custContactNo').val());
            sessionStorage.setItem("simNo", $('#simNo').val());
            sessionStorage.setItem("mobileNo", $('#mobileNo').val());
            sessionStorage.setItem("remark", $('#remark').val());
            sessionStorage.setItem("reversalHistory", $('#reversalHistory').is(":checked"));
            sessionStorage.setItem("enableAmex", $('#enableAmex').is(":checked"));
            sessionStorage.setItem("pushNotification", $('#pushNotification').is(":checked"));
            sessionStorage.setItem("preAuth", $('#preAuth').is(":checked"));
            sessionStorage.setItem("keyInForAmex", $('#keyInForAmex').is(":checked"));
            sessionStorage.setItem("popupMessage", $('#popupMessage').is(":checked"));

            sessionStorage.setItem("autoReversal", $('#autoReversal').is(":checked"));
            sessionStorage.setItem("printless", $('#printless').is(":checked"));

            sessionStorage.setItem("cardTypeValidation", $('#cardTypeValidation').is(":checked"));
            sessionStorage.setItem("saleReceipt", $('#saleReceipt').is(":checked"));
            sessionStorage.setItem("dccPayload", $('#dccPayload').is(":checked"));

            sessionStorage.setItem("currencyFromBin", $('#currencyFromBin').is(":checked"));
            sessionStorage.setItem("currencyFromCard", $('#currencyFromCard').is(":checked"));
            sessionStorage.setItem("proceedWithLkr", $('#proceedWithLkr').is(":checked"));

            sessionStorage.setItem("cardTap", $('#cardTap').is(":checked"));
            sessionStorage.setItem("cardInsert", $('#cardInsert').is(":checked"));
            sessionStorage.setItem("cardSwipe", $('#cardSwipe').is(":checked"));

            sessionStorage.setItem("network2g", $('#network2g').is(":checked"));
            sessionStorage.setItem("network3g", $('#network3g').is(":checked"));
            sessionStorage.setItem("network4g", $('#network4g').is(":checked"));

            sessionStorage.setItem("merchantPortal", $('#merchantPortal').is(":checked"));
            sessionStorage.setItem("resendVoid", $('#resendVoid').is(":checked"));
            sessionStorage.setItem("clientCredentials", $('#clientCredentials').is(":checked"));
            sessionStorage.setItem("printReceipt", $('#printReceipt').is(":checked"));

            sessionStorage.setItem("keyInAmex", $('#keyInAmex').is(":checked"));
            sessionStorage.setItem("autoChange", $('#autoChange').is(":checked"));

            sessionStorage.setItem("voidPwd", $('#voidPwd').val());
            //	sessionStorage.setItem("diffSaleMidTid",$('#diffSaleMidTid').is(":checked"));
            sessionStorage.setItem("midtidSeg", $('#midtidSeg').is(":checked"));
            sessionStorage.setItem("eventAutoUpdate", $('#eventAutoUpdate').is(":checked"));
            sessionStorage.setItem("newVoidPwd", $('#newVoidPwd').val());
            sessionStorage.setItem("newSettlementPwd", $('#newSettlementPwd').val());
            sessionStorage.setItem("imeiScan", $('#imeiScan').is(":checked"));
            sessionStorage.setItem("commission", $('#commission').is(":checked"));
            sessionStorage.setItem("commissionRate", $('#commissionRate').val());
            sessionStorage.setItem("refNumberEnable", $('#refNumberEnable').is(":checked"));
            sessionStorage.setItem("tleProfilesEnable", $('#tleProfilesEnable').is(":checked"));

            sessionStorage.removeItem("profileName");
            sessionStorage.removeItem("profileMerchantName");
            sessionStorage.removeItem("profileMerchantAddress");

            sessionStorage.removeItem("vContact");
            sessionStorage.removeItem("vConless");
            sessionStorage.removeItem("visaNoCvm");
            sessionStorage.removeItem("visaTransLimit");

            sessionStorage.removeItem("mcContact");
            sessionStorage.removeItem("mcConless");
            sessionStorage.removeItem("mcNoCvm");
            sessionStorage.removeItem("mcTransLimit");

            sessionStorage.removeItem("amexContact");
            sessionStorage.removeItem("amexConless");
            sessionStorage.removeItem("amexNoCvm");
            sessionStorage.removeItem("amexTransLimit");

            sessionStorage.removeItem("upayContact");
            sessionStorage.removeItem("upayConless");
            sessionStorage.removeItem("upayNoCvm");
            sessionStorage.removeItem("upayTransLimit");

            sessionStorage.removeItem("jcbContact");
            sessionStorage.removeItem("jcbConless");
            sessionStorage.removeItem("jcbNoCvm");
            sessionStorage.removeItem("jcbTransLimit");
            sessionStorage.removeItem("customerCopy");
            sessionStorage.removeItem("tlsEnable");
        }
    });

    $("#configProfileMerchant").click(function () {
        if (($('#profileName').val() != '') || ($('#profileName').val().length > 0)) {
            sessionStorage.setItem("profileName", $('#profileName').val());
            sessionStorage.setItem("profileMerchantName", $('#profileMerchantName').val());
            sessionStorage.setItem("profileMerchantAddress", $('#profileMerchantAddress').val());

            sessionStorage.setItem("vContact", $('#vContact').is(":checked"));
            sessionStorage.setItem("vConless", $('#vConless').is(":checked"));
            sessionStorage.setItem("visaNoCvm", $('#visaNoCvm').val());
            sessionStorage.setItem("visaTransLimit", $('#visaTransLimit').val());

            sessionStorage.setItem("mcContact", $('#mcContact').is(":checked"));
            sessionStorage.setItem("mcConless", $('#mcConless').is(":checked"));
            sessionStorage.setItem("mcNoCvm", $('#mcNoCvm').val());
            sessionStorage.setItem("mcTransLimit", $('#mcTransLimit').val());

            sessionStorage.setItem("amexContact", $('#amexContact').is(":checked"));
            sessionStorage.setItem("amexConless", $('#amexConless').is(":checked"));
            sessionStorage.setItem("amexNoCvm", $('#amexNoCvm').val());
            sessionStorage.setItem("amexTransLimit", $('#amexTransLimit').val());

            sessionStorage.setItem("upayContact", $('#upayContact').is(":checked"));
            sessionStorage.setItem("upayConless", $('#upayConless').is(":checked"));
            sessionStorage.setItem("upayNoCvm", $('#upayNoCvm').val());
            sessionStorage.setItem("upayTransLimit", $('#upayTransLimit').val());

            sessionStorage.setItem("jcbContact", $('#jcbContact').is(":checked"));
            sessionStorage.setItem("jcbConless", $('#jcbConless').is(":checked"));
            sessionStorage.setItem("jcbNoCvm", $('#jcbNoCvm').val());
            sessionStorage.setItem("jcbTransLimit", $('#jcbTransLimit').val()); //
            sessionStorage.setItem("customerCopy", $('#customerCopy').is(":checked"));
            sessionStorage.setItem("tlsEnable", $('#tlsEnable').is(":checked"));

        }
    });




    $(function () {
        var tittle = document.title;
        console.log("Title:" + tittle);
        /*if((tittle=='Add Profile')||(tittle=='Modify Profile')){
            $('#profileName').val(sessionStorage.getItem("profileName"));
            $('#merchantName').val(sessionStorage.getItem("merchantName"));
                $('#merchantAddress').val(sessionStorage.getItem("merchantAddress"));
        }*/
        if ((sessionStorage.getItem("profileName") !== null)) {
            $('#profileMerchantName').val(sessionStorage.getItem("profileMerchantName"));
            //if($('#profileMerchantName').val() == ''){$('#profileMerchantName').val(sessionStorage.getItem("profileMerchantName"));}
            console.log("profile Name (ssn): " + sessionStorage.getItem("profileName"));
            $('#profileName').val(sessionStorage.getItem("profileName"));
            console.log("profile Name (fld): " + $('#profileName').val());
            //$('#profileMerchantAddress').val(sessionStorage.getItem("profileMerchantAddress"));
            if ($('#profileMerchantAddress').val() == '') { $('#profileMerchantAddress').val(sessionStorage.getItem("profileMerchantAddress")); }

            $("#vContact").prop('checked', (sessionStorage.getItem("vContact") == 'true') ? true : false);
            $("#vConless").prop('checked', (sessionStorage.getItem("vConless") == 'true') ? true : false);
            $('#visaNoCvm').val(sessionStorage.getItem("visaNoCvm"));
            $('#visaTransLimit').val(sessionStorage.getItem("visaTransLimit"));

            $("#mcContact").prop('checked', (sessionStorage.getItem("mcContact") == 'true') ? true : false);
            $("#mcConless").prop('checked', (sessionStorage.getItem("mcConless") == 'true') ? true : false);
            $('#mcNoCvm').val(sessionStorage.getItem("mcNoCvm"));
            $('#mcTransLimit').val(sessionStorage.getItem("mcTransLimit"));

            $("#amexContact").prop('checked', (sessionStorage.getItem("amexContact") == 'true') ? true : false);
            $("#amexConless").prop('checked', (sessionStorage.getItem("amexConless") == 'true') ? true : false);
            $('#amexNoCvm').val(sessionStorage.getItem("amexNoCvm"));
            $('#amexTransLimit').val(sessionStorage.getItem("amexTransLimit"));

            $("#upayContact").prop('checked', (sessionStorage.getItem("upayContact") == 'true') ? true : false);
            $("#upayConless").prop('checked', (sessionStorage.getItem("upayConless") == 'true') ? true : false);
            $('#upayNoCvm').val(sessionStorage.getItem("upayNoCvm"));
            $('#upayTransLimit').val(sessionStorage.getItem("upayTransLimit"));

            $("#jcbContact").prop('checked', (sessionStorage.getItem("jcbContact") == 'true') ? true : false);
            $("#jcbConless").prop('checked', (sessionStorage.getItem("jcbConless") == 'true') ? true : false);
            $('#jcbNoCvm').val(sessionStorage.getItem("jcbNoCvm"));
            $('#jcbTransLimit').val(sessionStorage.getItem("jcbTransLimit"));
            $("#customerCopy").prop('checked', (sessionStorage.getItem("customerCopy") == 'true') ? true : false);
            $("#tlsEnable").prop('checked', (sessionStorage.getItem("tlsEnable") == 'true') ? true : false);
            $("#jcbConless").prop('checked', (sessionStorage.getItem("jcbConless") == 'true') ? true : false);
        }

        function pad(str, max) {
            str = str.toString();
            return str.length < max ? pad("0" + str, max) : str;
        }

        function updateData() {

            let daysArray = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
            const months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
            let day = new Date().getDay();
            let dayName = daysArray[day];

            const d = new Date();
            let month = months[d.getMonth()];
            let year = d.getFullYear();
            let date = d.getDate();
            var currentDate = date + " " + month + "," + year;

            var today = new Date();
            var cHour = today.getHours();
            var cMin = today.getMinutes();
            var cSec = today.getSeconds();
            //console.log(pad(cHour,2) + ":" + pad(cMin,2)+ ":" + pad(cSec,2) );
            $('#currentTime').text(pad(cHour, 2) + ":" + pad(cMin, 2) + ":" + pad(cSec, 2));
            $('#dayOfWeekName').text(dayName);
            $('#currentDate').text(currentDate);

        }
        const settings = {
            "async": true,
            "crossDomain": true,
            "url": "http://127.0.0.1:2020/admin-portal/api/v1/devices/userstatus",
            "method": "GET",
            "headers": {
                "Content-Type": "application/json",
                "Authorization": "Basic YWRtaW46QWRtaW5AMTIz"
            },
            "processData": false,
            "data": ""
        };
        function callAPI() {
            $.ajax(settings).done(function (response) {
                console.log(response);
            });
        };
        updateData(); // This will run on page load
        setInterval(function () {
            var tittle = document.title;
            if (tittle == 'Dashboard') {
                updateData() // this will run after every 1 seconds
                //callAPI();
            }
        }, 1000);


    });

    $(function () {
        var tittle = document.title;
        console.log(tittle);
        if ((tittle == 'Add Profile') || (tittle == 'Modify Profile')) {

            if ($('#profileMerchantName').val() == null || $('#profileMerchantName').val() == '') {
                $('#profileMerchantName').val(sessionStorage.getItem("merchantName"));
            }

            if ($('#profileMerchantAddress').val() == null || $('#profileMerchantAddress').val() == '') {
                $('#profileMerchantAddress').val(sessionStorage.getItem("merchantAddress"));
            }
        }

    });

    $(function () {
        var tittle = $(document).find("title").text();
        if (((tittle == 'Register Device') || (tittle == 'Modify Device') || (tittle == 'Add Profile')) && ((sessionStorage.getItem("serialNo") !== null))) {
            console.log("ecr");
            $('#bankName').val(sessionStorage.getItem("bankName"));
            $('#serialNo').val(sessionStorage.getItem("serialNo"));
            $('#merchantName').val(sessionStorage.getItem("merchantName"));
            $('#merchantAddress').val(sessionStorage.getItem("merchantAddress"));
            $("#noSettle").prop('checked', (sessionStorage.getItem("noSettle") == 'true') ? true : false);
            $("#mkiPreAuth").prop('checked', (sessionStorage.getItem("mkiPreAuth") == 'true') ? true : false);
            $("#mkiOffline").prop('checked', (sessionStorage.getItem("mkiOffline") == 'true') ? true : false);
            $("#forceSettle").prop('checked', (sessionStorage.getItem("forceSettle") == 'true') ? true : false);
            $("#autoSettle").prop('checked', (sessionStorage.getItem("autoSettle") == 'true') ? true : false); $("#lkrDefaultCurr1").prop('checked', (sessionStorage.getItem("lkrDefaultCurr1") == 'true') ? true : false);
            $("#signPriority").prop('checked', (sessionStorage.getItem("signPriority") == 'true') ? true : false);
            $("#blockMag").prop('checked', (sessionStorage.getItem("blockMag") == 'true') ? true : false);
            $("#customerReceipt").prop('checked', (sessionStorage.getItem("customerReceipt") == 'true') ? true : false);
            $('#autoSettleTime').val(sessionStorage.getItem("autoSettleTime"));
            $("#ecr").prop('checked', (sessionStorage.getItem("ecr") == 'true') ? true : false);
            $("#ecrQr").prop('checked', (sessionStorage.getItem("ecrQr") == 'true') ? true : false);
            $("#signature").prop('checked', (sessionStorage.getItem("signature") == 'true') ? true : false);
            $("#debugMode").prop('checked', (sessionStorage.getItem("debugMode") == 'true') ? true : false);
            $("#keyIn").prop('checked', (sessionStorage.getItem("keyIn") == 'true') ? true : false);
            $("#activityTracker").attr('checked', (sessionStorage.getItem("activityTracker") == 'true') ? true : false);
            $("#qrRefund").attr('checked', (sessionStorage.getItem("qrRefund") == 'true') ? true : false);
            $('#custContactNo').val(sessionStorage.getItem("custContactNo"));
            $('#simNo').val(sessionStorage.getItem("simNo"));
            $('#mobileNo').val(sessionStorage.getItem("mobileNo"));
            $('#remark').val(sessionStorage.getItem("remark"));
            $("#reversalHistory").prop('checked', (sessionStorage.getItem("reversalHistory") == 'true') ? true : false);
            $("#pushNotification").prop('checked', (sessionStorage.getItem("pushNotification") == 'true') ? true : false);
            $("#enableAmex").prop('checked', (sessionStorage.getItem("enableAmex") == 'true') ? true : false);
            $("#preAuth").prop('checked', (sessionStorage.getItem("preAuth") == 'true') ? true : false);
            $("#keyInForAmex").prop('checked', (sessionStorage.getItem("keyInForAmex") == 'true') ? true : false);
            $("#popupMessage").prop('checked', (sessionStorage.getItem("popupMessage") == 'true') ? true : false);

            $("#autoReversal").prop('checked', (sessionStorage.getItem("autoReversal") == 'true') ? true : false);
            $("#printless").prop('checked', (sessionStorage.getItem("printless") == 'true') ? true : false);

            $("#cardTypeValidation").prop('checked', (sessionStorage.getItem("cardTypeValidation") == 'true') ? true : false);
            $("#saleReceipt").prop('checked', (sessionStorage.getItem("saleReceipt") == 'true') ? true : false);
            $("#dccPayload").prop('checked', (sessionStorage.getItem("dccPayload") == 'true') ? true : false);

            $("#currencyFromBin").prop('checked', (sessionStorage.getItem("currencyFromBin") == 'true') ? true : false);
            $("#currencyFromCard").prop('checked', (sessionStorage.getItem("currencyFromCard") == 'true') ? true : false);
            $("#proceedWithLkr").prop('checked', (sessionStorage.getItem("proceedWithLkr") == 'true') ? true : false);

            $("#cardTap").prop('checked', (sessionStorage.getItem("cardTap") == 'true') ? true : false);
            $("#cardInsert").prop('checked', (sessionStorage.getItem("cardInsert") == 'true') ? true : false);
            $("#cardSwipe").prop('checked', (sessionStorage.getItem("cardSwipe") == 'true') ? true : false);

            $("#network2g").prop('checked', (sessionStorage.getItem("network2g") == 'true') ? true : false);
            $("#network3g").prop('checked', (sessionStorage.getItem("network3g") == 'true') ? true : false);
            $("#network4g").prop('checked', (sessionStorage.getItem("network4g") == 'true') ? true : false);

            $("#merchantPortal").prop('checked', (sessionStorage.getItem("merchantPortal") == 'true') ? true : false);
            $("#resendVoid").prop('checked', (sessionStorage.getItem("resendVoid") == 'true') ? true : false);
            $("#clientCredentials").prop('checked', (sessionStorage.getItem("clientCredentials") == 'true') ? true : false);
            $("#printReceipt").prop('checked', (sessionStorage.getItem("printReceipt") == 'true') ? true : false);

            $("#keyInAmex").prop('checked', (sessionStorage.getItem("keyInAmex") == 'true') ? true : false);
            $("#autoChange").prop('checked', (sessionStorage.getItem("autoChange") == 'true') ? true : false);

            $('#voidPwd').val(sessionStorage.getItem("voidPwd"));
            $("#diffSaleMidTid").prop('checked', (sessionStorage.getItem("diffSaleMidTid") == 'true') ? true : false);
            $("#midtidSeg").prop('checked', (sessionStorage.getItem("midtidSeg") == 'true') ? true : false);
            $("#eventAutoUpdate").prop('checked', (sessionStorage.getItem("eventAutoUpdate") == 'true') ? true : false);
            $('#newVoidPwd').val(sessionStorage.getItem("newVoidPwd"));
            $('#newSettlementPwd').val(sessionStorage.getItem("newSettlementPwd"));
            $("#imeiScan").prop('checked', (sessionStorage.getItem("imeiScan") == 'true') ? true : false);
            $("#commission").prop('checked', (sessionStorage.getItem("commission") == 'true') ? true : false);
            $("#refNumberEnable").prop('checked', (sessionStorage.getItem("refNumberEnable") == 'true') ? true : false);
            $('#commissionRate').val(sessionStorage.getItem("commissionRate"));
            $("#tleProfilesEnable").prop('checked', (sessionStorage.getItem("tleProfilesEnable") == 'true') ? true : false);



            if ($('#ecr').is(':checked')) {
                console.log("ecr Active");
                $('#ecrSettings').show();

            } else {
                $('#ecrSettings').hide();
                console.log("ecr inactive");
            }

            if ($('#keyIn').is(':checked')) {
                $('#key-in-for-amex').show();

            } else {
                $('#key-in-for-amex').hide();
            }

            checkChk();
        }
    });
    $(function () {
        var val = $('#type').val()
        if (val == 'SALE') {
            $('#div-qr').hide();
            $('#div-solo').hide();
            $('#div-amex').hide();
        } else if (val == 'QR') {
            $('#div-qr').show();
            if (sessionStorage.getItem("bankName") == 'HNB') {
                $('#div-solo').show();
            } else
                $('#div-solo').hide();
            $('#div-amex').hide();
        } else if (val == 'AMEX') {
            $('#div-amex').show();
            $('#div-qr').hide();
            $('#div-solo').hide();
        } else {
            $('#div-qr').hide();
            $('#div-solo').hide();
            $('#div-amex').hide();
        }
    });


    $("#navReg,#navEvnt,#navdev,#navAct,#btnModifyDevice,#btnEditD").on("click", function () {	//	$("#navReg,#navEvnt,#navdev,#navAct,#btnModifyDevice,#btnEditDevice,#btnSaveDevice").on("click", function()
        console.log("Session Storage Cleared")
        sessionStorage.clear();
    });

    $("#reportToDate,#reportFromDate").on("click", function () {
        dt = new Date();
        var max = (dt.getFullYear() + "-" + zeroPadded(dt.getMonth() + 1) + "-" + zeroPadded(dt.getDate()));
        dt.setDate(dt.getDate() - 14)
        var min = (dt.getFullYear() + "-" + zeroPadded(dt.getMonth() + 1) + "-" + zeroPadded(dt.getDate()));
        $("#reportToDate,#reportFromDate").attr({
            "max": max,
            "min": min
        })

    });
});

$(window).on('load', function () {
    var delayMs = 10; // delay in milliseconds

    setTimeout(function () {
        $('#modalSuccess').modal('show');
    }, delayMs);

    setTimeout(function () {
        $('#modalFail').modal('show');
    }, delayMs);
});

function configProfMerchant() {
    console.log("configProfMerchant clicked ");
    if (($('#profileName').val() != '') || ($('#profileName').val().length > 0)) {

        console.log("configProfMerchant " + $('#profileName').val());

        sessionStorage.setItem("profileName", $('#profileName').val());
        sessionStorage.setItem("profileMerchantName", $('#profileMerchantName').val());
        sessionStorage.setItem("profileMerchantAddress", $('#profileMerchantAddress').val());

        sessionStorage.setItem("vContact", $('#vContact').is(":checked"));
        sessionStorage.setItem("vConless", $('#vConless').is(":checked"));
        sessionStorage.setItem("visaNoCvm", $('#visaNoCvm').val());
        sessionStorage.setItem("visaTransLimit", $('#visaTransLimit').val());

        sessionStorage.setItem("mcContact", $('#mcContact').is(":checked"));
        sessionStorage.setItem("mcConless", $('#mcConless').is(":checked"));
        sessionStorage.setItem("mcNoCvm", $('#mcNoCvm').val());
        sessionStorage.setItem("mcTransLimit", $('#mcTransLimit').val());

        sessionStorage.setItem("amexContact", $('#amexContact').is(":checked"));
        sessionStorage.setItem("amexConless", $('#amexConless').is(":checked"));
        sessionStorage.setItem("amexNoCvm", $('#amexNoCvm').val());
        sessionStorage.setItem("amexTransLimit", $('#amexTransLimit').val());

        sessionStorage.setItem("upayContact", $('#upayContact').is(":checked"));
        sessionStorage.setItem("upayConless", $('#upayConless').is(":checked"));
        sessionStorage.setItem("upayNoCvm", $('#upayNoCvm').val());
        sessionStorage.setItem("upayTransLimit", $('#upayTransLimit').val());

        sessionStorage.setItem("jcbContact", $('#jcbContact').is(":checked"));
        sessionStorage.setItem("jcbConless", $('#jcbConless').is(":checked"));
        sessionStorage.setItem("jcbNoCvm", $('#jcbNoCvm').val());
        sessionStorage.setItem("jcbTransLimit", $('#jcbTransLimit').val());
        sessionStorage.setItem("customerCopy", $('#customerCopy').is(":checked"));
        sessionStorage.setItem("tlsEnable", $('#tlsEnable').is(":checked"));


    }
    return true;
}

function goBack() {
    window.history.back();
}
function CheckSearchText() {
    return ($('#searchData').val() == '') ? false : true;
}
function CheckSearchSerialNo() {
    return ($('#serialNo').val() == '') ? false : true;
}
function CheckSearchDates() {
    return ($('#startDate').val() == '') || ($('#endDate').val() == '') ? false : true;
}
function CheckSearchIds() {
    return ($('#tid').val() == '') && ($('#mid').val() == '') ? false : true;
}
function validateDates() {
    var val = $('#eventType').val();
    var reportToDate = $('#reportToDate').val();
    var reportFromDate = $('#reportFromDate').val();
    if ((val == 'ACTIVITY_REPORT') && (reportToDate == '' || reportFromDate == '')) {
        return false
    } else {
        return true
    }

}
function zeroPadded(val) {
    if (val >= 10)
        return val;
    else
        return '0' + val;
}

function checkChk() {
    if (sessionStorage.getItem("noSettle") == 'true') {
        $('#autoSettle').attr('disabled', true); $('#forceSettle').attr('disabled', true); $("#autoSettleTime").prop("disabled", true);
    }
    else if (sessionStorage.getItem("forceSettle") == 'true') {
        $('#autoSettle').attr('disabled', true); $('#noSettle').attr('disabled', true); $("#autoSettleTime").prop("disabled", true);
    }
    else if (sessionStorage.getItem("autoSettle") == 'true') {
        $('#forceSettle').attr('disabled', true); $('#noSettle').attr('disabled', true);
    }
}
function showNotification() {
    $('#dev-notify').show();
    return true;
}

function showChangePassword() {
    $('#div_password').show();
    $('#div_username').hide();
    $('#div_displayname').hide();

    return true;
}
function showChangeUsername() {
    $('#div_username').show();
    $('#div_password').hide();
    $('#div_displayname').hide();

    return true;
}
function showChangeDisplayname() {
    $('#div_displayname').show();
    $('#div_password').hide();
    $('#div_username').hide();

    return true;
}
function checkPassword(id) {
    var pw = $("input[id='newPassword_" + id + "']").val();
    var pw2 = $("input[id='reNewPassword_" + id + "']").val();
    $("input[id='userId_" + id + "']").val(id);
    try {

        if (pw.length < 4) {
            $("#passwordsNoMatch_" + id).hide();
            $("#passwordsLengthMatch_" + id).show();
            return false;
        }
        if (pw != pw2) {
            $("#passwordsNoMatch_" + id).show();
            $("#passwordsLengthMatch_" + id).hide();
            return false;
        }
        return true;
    } catch (e) {
        $("#passwordsNoMatch_" + id).show();
        $("#passwordsLengthMatch_" + id).hide();
        return false;
    }


}
function clearProfileParams() {

    sessionStorage.removeItem("profileName");

    sessionStorage.removeItem("profileMerchantName");
    sessionStorage.removeItem("profileMerchantAddress");

    sessionStorage.removeItem("vContact");
    sessionStorage.removeItem("vConless");
    sessionStorage.removeItem("visaNoCvm");
    sessionStorage.removeItem("visaTransLimit");

    sessionStorage.removeItem("mcContact");
    sessionStorage.removeItem("mcConless");
    sessionStorage.removeItem("mcNoCvm");
    sessionStorage.removeItem("mcTransLimit");

    sessionStorage.removeItem("amexContact");
    sessionStorage.removeItem("amexConless");
    sessionStorage.removeItem("amexNoCvm");
    sessionStorage.removeItem("amexTransLimit");

    sessionStorage.removeItem("upayContact");
    sessionStorage.removeItem("upayConless");
    sessionStorage.removeItem("upayNoCvm");
    sessionStorage.removeItem("upayTransLimit");

    sessionStorage.removeItem("jcbContact");
    sessionStorage.removeItem("jcbConless");
    sessionStorage.removeItem("jcbNoCvm");
    sessionStorage.removeItem("jcbTransLimit");
    sessionStorage.removeItem("customerCopy");
    sessionStorage.removeItem("tlsEnable");
    console.log("Profile Param cleared");
    return true;
}

function setMidTid() {
    $('#mid').val(sessionStorage.getItem("mid"));
    $('#tid').val(sessionStorage.getItem("tid"));
    return true;
}


//added by kavindu balasooriya


function backToTop() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}


function scrollFunction() {
    if (
        document.body.scrollTop > 2 ||
        document.documentElement.scrollTop > 2
    ) {

        $('#btn-back-to-top').css("display", "block")
    } else {
        $('#btn-back-to-top').css("display", "none")
    }
}

$(document).ready(function () {
    function updateProfileVisibility() {

        const isMidtidSegChecked = $('#midtidSeg').is(":checked");
        const bankNameValue = $('#bankName').val();
        const isBankNameComBank = bankNameValue === 'COM_BANK';
        console.log(isBankNameComBank);
        console.log(isMidtidSegChecked);

        if (isMidtidSegChecked || sessionStorage.getItem("midtidSeg") == 'true') {
            $('#addProfile_diff').show();
            $('#addProfile_norm').hide();
            $('#editProfile_diff').show();
            $('#editProfile_norm').hide();
            $('#editProfile_com').hide();
            $('#addProfile_com_bank').hide();
            console.log("Mode-D");
        } else if (isBankNameComBank || sessionStorage.getItem("bankName") == 'COM_BANK') {
            $('#addProfile_norm').hide();
            $('#addProfile_diff').hide();
            $('#editProfile_norm').hide();
            $('#editProfile_diff').hide();
            $('#editProfile_com').show();
            $('#addProfile_com_bank').show();
            console.log("Mode-C");
        } else {
            $('#addProfile_norm').show();
            $('#addProfile_diff').hide();
            $('#editProfile_norm').show();
            $('#editProfile_diff').hide();
            $('#addProfile_com_bank').hide();
            $('#editProfile_com').hide();
            console.log("Mode-N");
        }
    }

    //  updateProfileVisibility();

    $("#midtidSeg").on("change", function () {
        sessionStorage.setItem("midtidSeg", $('#midtidSeg').is(":checked"));
        updateProfileVisibility();
    });

    $("#bankName").on("change", function () {
        sessionStorage.setItem("bankName", $('#bankName').val());
        updateProfileVisibility();
    });
});

$(document).ready(function () {
    function updateProfileModify() {
        const isMidtidSegChecked = $('#midtidSeg').is(":checked");
        const bankNameValue = $('#bankName').val();
        const isBankNameComBank = bankNameValue === 'COM_BANK';
        console.log("Table row count:");
        $('table.profileTable tbody tr').each(function () {
            if (isMidtidSegChecked || sessionStorage.getItem("midtidSeg") == 'true') {
                $(this).find('.D').show();
                $(this).find('.N').hide();
                $(this).find('.C').hide();
                console.log("D");
            } else if (isBankNameComBank || sessionStorage.getItem("bankName") == 'COM_BANK') {
                $(this).find('.C').show();
                $(this).find('.N').hide();
                $(this).find('.D').hide();
                console.log("C");
            } else {
                $(this).find('.N').show();
                $(this).find('.D').hide();
                $(this).find('.C').hide();
                console.log("N");
            }
        });
    }

    updateProfileModify();
    $("#midtidSeg").on("change", function () {
        sessionStorage.setItem("midtidSeg", $('#midtidSeg').is(":checked"));
        updateProfileModify();
    });

    $("#bankName").on("change", function () {
        sessionStorage.setItem("bankName", $('#bankName').val());
        updateProfileModify();
    });
});

// show settings password
function showSettingPassword(serialNo, deviceId) {
    console.log("Serial No: " + serialNo + " deviceId: " + deviceId);
    fetch('/admin-portal/view-password/' + serialNo, {
        method: 'GET'
    })
        .then(res => {
            if (res.status === 401) {
                console.log('Unauthorized');
                window.location.href = "/login";
                return 'Unauthorized-Time out'
            }
            if (res.status != 200) {
                console.log('not found');
                document.getElementById('oldPassword').style.display = 'none' ;
                document.getElementById('newPassword').style.display = 'none' ;
                document.getElementById('massage').style.display = 'none' ;
                document.getElementById('massage2').style.display = 'none' ;
                document.getElementById('error').style.display = 'block' ;
                return 'An error occurred. Please try again.'
            }
            return res.json();
        })
        .then(data => {
            console.log('Response from back end:', data);
            document.getElementById('error' + deviceId).innerText = data;
            document.getElementById('oldPassword'+ deviceId).textContent  = data.OLD ;
            document.getElementById('newPassword'+ deviceId).textContent  = data.NEW ;
            //set model id
            let id = "models" + deviceId;
            var myModal = new bootstrap.Modal(document.getElementById(id), {});
            //show model
            myModal.show();
        })
        .catch(error =>{
            console.error('Error:', error);
            document.getElementById('oldPassword').style.display = 'none' ;
            document.getElementById('newPassword').style.display = 'none' ;
            document.getElementById('massage').style.display = 'none' ;
            document.getElementById('massage2').style.display = 'none' ;
            document.getElementById('error' + deviceId).innerText = error;
        });
}

/*  save Device and Modify Device button enable
when back to the device page
*/
$(window).on("pageshow", function (event) {

    if (event.originalEvent.persisted ||
        performance.getEntriesByType("navigation")[0].type === "back_forward") {

        // Handle logic for back navigation
        const isButtonDisabled = sessionStorage.getItem("isButtonDisabled")
        if (isButtonDisabled) {
            console.log("set button disabled false");
            $("#btnSaveDevice").attr("disabled", false);
            $("#btnModifyDevice").attr("disabled", false);
            $('#dev-notify').hide();
            sessionStorage.removeItem("isButtonDisabled")
        }
    }
});