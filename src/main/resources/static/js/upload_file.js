
//上传文件按钮
$("#uploadFilesBtn").click(function () {

    var files = $('#files')[0].files;
    if (files.length == 0) {
        $("#mustChoseFile").show();
        $("#mustChoseFile").html("请至少选择一个文件");
        return false;
    }

    // 进度条归零
    $("#progressBar").width("0%");
    // 上传按钮禁用
    $("#uploadFilesBtn").attr("disabled", true);
    // 进度条显示
    $("#progressBar").parent().show();
    $("#progressBar").parent().addClass("active");
    upload();
})

function refreshBtn() {
    $("#files").val("");
    $("#uploadFilesBtn").attr("disabled", "disabled");
}

function upload() {
    var formData = new FormData();
    var files = $('#files')[0].files;
    for (var i = 0; i < files.length; i++) {
        formData.append('files', files[i]);
    }
    function onprogress(evt) {
        // 写要实现的内容
        var progressBar = $("#progressBar");
        if (evt.lengthComputable) {
            var completePercent = Math.round(evt.loaded / evt.total * 100);
            progressBar.width(completePercent + "%");
            $("#progressBar").text(completePercent + "%");
        }
    }

    var xhr_provider = function () {
        var xhr = jQuery.ajaxSettings.xhr();
        if (onprogress) {
            if (xhr.upload) {
                xhr.upload.addEventListener('progress', onprogress, false);
            }
        }
        return xhr;
    };
    var base = window.location.protocol + "//" + window.location.host;
    $.ajax({
        url: base + "/uploadFiles",
        dataType : "html",
        type: 'POST',
        cache: false,
        data: formData,
        processData: false,
        contentType: false,
        xhr: xhr_provider,
        success: function (data) {
            var result = $.parseJSON(data);
            if (result['successList'] != null) {
                $("#successList").show();
                $("#successList").html("文件 [ " + result['successList'].join(" , ") + " ] 上传成功");
                sessionStorage.setItem("isUpload", true);//文件上传成功,刷新列表
            }
            if (result['failList'] != null) {
                $("#failList").show();
                $("#failList").html("文件 [ " + result['failList'].join(" , ") + " ] 上传失败");
            }

            // 进度条隐藏
            $("#progressBar").parent().hide();
            refreshBtn();
        },
        error: function (data) {
            $("#failList").show();
            $("#failList").html("文件上传失败,请联系系统管理员!");
            refreshBtn();
        }
    })
}

$("#files").change(function () {
    var files = $('#files')[0].files;
    if (files.length > 0) {
        $("#uploadFilesBtn").removeAttr("disabled");
    }else {
        $("#uploadFilesBtn").attr("disabled", "disabled");
    }
})

$("#files").click(function () {
    $("#failList").hide();
    $("#successList").hide();
})

$("#reloadFileList").click(function () {
    if (sessionStorage.getItem("isUpload") == 'true') {
        window.parent.location.reload(true);
    }
    sessionStorage.setItem("isUpload", false);
})

function batchDelete() {
    var ids = new Array();
    var filePaths = new Array();
    $("input[name='choseElement']:checked").each(function (index, item) {
        var id = item.value.split(",")[0];
        var filePath =  item.value.split(",")[1];
        ids.push(id);
        filePaths.push(filePath);
    });
    if (ids.length > 0) {
        var url = window.location.protocol + "//" + window.location.host + "/deleteFiles?ids=" + ids + "&filePaths=" + filePaths;
        $.ajax({
            url: url,
            dataType : "json",
            type: 'GET',
            success: function (data) {
                window.parent.location.reload(true);
            },
            error: function (data) {
                //window.parent.location.reload(true);
            }
        })
    }
}

function setDeleteFile(id, filePath) {
    $("#id").val(id);
    $("#filePath").val(filePath);
}

function deleteFile() {
    var id = $("#id").val();
    var filePath = $("#filePath").val();
    var url = window.location.protocol + "//" + window.location.host + "/deleteFile?id=" + id + "&filePath=" + filePath;
    $.ajax({
        url: url,
        dataType : "json",
        type: 'GET',
        success: function (data) {
            window.parent.location.reload(true);
        },
        error: function (data) {
            //window.parent.location.reload(true);
        }
    })
}
