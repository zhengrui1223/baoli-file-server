function setDeleteFile(id, filePath) {
    $("#id").val(id);
    $("#filePath").val(filePath);
}

function deleteFile() {
    $("#deleteFileForm").submit();
}

$(function () {
    //上传文件按钮
    $("#uploadFilesBtn").click(function () {
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
        setTimeout(function () {
            $("#uploadFilesBtn").text("上传文件");
            $("#uploadFilesBtn").removeAttr("disabled");
        }, 1500);
    }

    function upload() {
        var formData = new FormData();
        //formData.append('file', $('#singleFile')[0].files[0]);
        //formData.append('files', $('#files')[0].files);
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
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false,
            xhr: xhr_provider,
            success: function (data) {
                console.info(data);
                var result = $.parseJSON(data);
                if (result.code == "0") {
                    $("#uploadFilesBtn").text("上传成功");
                    //$("#imageUpload").attr("src", ctx + result.data);
                    setTimeout(function () {
                        $("#uploadFilesBtn").text("上传文件");
                    }, 1000);
                } else if (result.code == "-4") {
                    $("#uploadFilesBtn").text("不支持的文件类型");
                    //ShowFailure("操作失败：" + result.data);
                } else {
                    $("#uploadFilesBtn").text(result.data);
                    //ShowFailure("操作失败：" + result.data);
                }
                // 进度条隐藏
                $("#progressBar").parent().hide();
                refreshBtn();
            },
            error: function (data) {
                console.info(data);
                //ShowFailure("操作失败：" + data);
                refreshBtn();
            }
        })
    }
})
