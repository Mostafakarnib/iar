/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




$(document).click(function (e) {
    var container = $('.action-popover');
    console.log(e.target);
    if (!container.is(e.target) && container.has(e.target).length === 0) {
        if ($(e.target).hasClass('open-popover')) {
            console.log('trueeeeeee');
        } else {
            container.removeClass('active');
        }
    }
});

$(document).on('ready', function () {
    initializePlugins();
});

$(document).on('click', '.cancel-delete , .delete', function () {
    $(this).closest('.popover').removeClass('active');
    return false;
});

$(document).on('click', '.open-uploader', function () {
    $('.uploader').find('[type="file"]').click();
});

function handleAjax(data) {
    var status = data.status;

    switch (status) {
        case "begin":
            // This is the start of the AJAX request.

            if (data.source.id === 'loader_id')
            {
                $('.loader-dlg').modal({backdrop: 'static'});
            }
            break;

        case "complete":
            // This is invoked right after AJAX response is returned.
            if (data.source.id === 'loader_id')
            {
                $('.loader-dlg').modal('hide');
            }

            break;

        case "success":
            // This is invoked right after successful processing of AJAX response
            // and update of HTML DOM.
            initializePlugins();
            break;
    }
}

function initializePlugins() {

    $('[data-action]').keypress(function (e) {
        if (e.which === 13) {
            var action = $(this).attr('data-action');
            $('#' + action).click();
            return false;
        }
    });

    $("select.select2").select2({
        width: "100%",
        rtl: false,
        multiple: false
    });

    $("select.select2-multi").select2({
        multiple: true,
        width: "100%",
        rtl: false,
        theme: "classic"
    });

    $("select.select2-tags").select2({
        width: "100%",
        rtl: false,
        theme: "classic",
        multiple: true,
        tags: true
    });

    $('.datepicker').datepicker({
        autoclose: true,
        format: 'dd-mm-yyyy'
    });

    $('.datetimepicker').datetimepicker({

    });

    $('.check').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '0%'
    });

    $('.check2').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '100%'
    });
}


function openDeletePopover(target, popoverId) {
    var $popover = $('#' + popoverId);
    $popover.addClass('active');
    new Tether({
        element: $popover,
        target: $(target),
        offset: '20px 0',
        attachment: 'top right',
        targetAttachment: 'top left'
    });
}

function openGraphSettingsPopover(target, popoverId) {
    var $popover = $('#' + popoverId);
    $popover.addClass('active');
    new Tether({
        element: $popover,
        target: $(target),
        offset: '20px 0',
        attachment: 'top right',
        targetAttachment: 'middle left'
    });
}

function openRequestTicketUpdatePopover(target, popoverId) {
    var $popover = $('#' + popoverId);
    $popover.addClass('active');
    new Tether({
        element: $popover,
        target: $(target),
        offset: '20px 0',
        attachment: 'top right',
        targetAttachment: 'top left'
    });
}

function onDelete(data) {
    //do nothing
}

function importValues() {
    $('.values-importer').click();
}

function onLanguageChange() {
    location.reload();
}


function showToastr(detail, summary, time) {
    toastr.options = {
        timeOut: time,
        "positionClass": "toast-top-right"
    }
    if (summary === 'success') {
        toastr.success(detail);
    } else if (summary === 'warn') {
        toastr.warn(detail);
    } else if (summary === 'error') {
        toastr.error(detail);
    } else if (summary === 'info') {
        toastr.info(detail);
    }
}

if (typeof jsf !== 'undefined') {
    jsf.ajax.addOnEvent(handleAjax);
    jsf.ajax.addOnError(function (data) {

        initializePlugins();

        // This shows how to get the information via XPath, but it is not required. The error name can be found using data.errorName
        var errorName = data.responseXML.evaluate('//error/error-name', data.responseXML, null, XPathResult.STRING_TYPE, null);
        var message = 'AJAX Exception';
        message += '\nSource: ' + data.source.id;
        message += '\nValue:' + data.source.value;
        message += '\nError: ' + errorName.stringValue;
        message += '\nMessage: ' + data.errorMessage;
        console.log(message);
        //TODO Take Additional actions
    });
}


var dynamicColors = function () {
    var r = Math.floor(Math.random() * 255);
    var g = Math.floor(Math.random() * 255);
    var b = Math.floor(Math.random() * 255);
    return "rgba(" + r + "," + g + "," + b + " , 0.8)";
}


