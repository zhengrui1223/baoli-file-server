function activeTab(id) {
    $("#" + id).addClass('active').siblings().removeClass('active');
}

/**
 * 每个列表页面都会有一个form, id 约定为searchForm, 针对于列表进行提交, 记录查询条件等.
 *  form中至少有两个隐藏域
 *      name及id均为 pageNum, pageSize
 */
function changePage(pageNum, pageSize) {
    $("#pageNum").val(pageNum);
    $("#pageSize").val(pageSize);
    $("#searchForm").submit();
}

function changePageSize(pageSize) {
    if (pageSize != null && pageSize != '') {
        $("#pageNum").val(1);
        $("#pageSize").val(pageSize);
        $("#searchForm").submit();
    }
}

function changePageNum(pageNum) {
    if (pageNum != null && pageNum != '') {
        $("#pageNum").val(pageNum);
        $("#searchForm").submit();
    }
}

/**
 * 全选, 取消全选功能
 * 约定, 父checkbox的 id 为 checkAll
 * 约定, 子checkbox的 name 为 choseElement
 */
function checkAll() {
    if ($("#checkAll").prop("checked")) {
        $("input[name='choseElement']").each(function () {
            $(this).prop("checked", true);
        });
    } else {
        $("input[name='choseElement']").each(function () {
            $(this).prop("checked", false);
        });
    }
}

function clickElement(obj) {
    if (!$(obj).prop("checked")) {
        $("#checkAll").prop("checked", false);
    } else {
        var _checkedAll = true;
        $("input[name='choseElement']").each(function () {
            if (!$(this).prop("checked")) {
                _checkedAll = false;
            }
        });
        if (_checkedAll) {
            $("#checkAll").prop("checked", true);
        }
    }
}


