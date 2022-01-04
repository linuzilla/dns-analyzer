function delete_instance_message(imDelUrl, id) {
    var actionUrl = imDelUrl + '/' + id;

    ncuCommonLib.delete(actionUrl, function (err, data) {
    });
}

function update_badge(badgeUrl, imDelUrl, checkEvent) {
    function update_badge_function() {
        ncuCommonLib.get(badgeUrl, function(err,data) {
            if (err == null && data != null) {
                if (data.badge != null) {
                    $.each(data.badge, function (index, element) {
                        $(index).html(element);
                    });
                }
                if (data.messages != null && data.messages.length > 0) {
                    var list = new Array();
                    list.push('<div class="container"><div class="row"><div class="col-lg-12">');

                    for (var i = 0; i < data.messages.length; i++) {
                        var contents = new Array();

                        contents.push('<div class="alert alert-dismissible alert-danger">');
                        contents.push('<button type="button" class="close" data-dismiss="alert" onclick="');
                        contents.push("delete_instance_message('");
                        contents.push(imDelUrl + "',");
                        contents.push(data.messages[i].id + ');">&times;</button>');
                        contents.push('<p class="mb-0" >');
                        contents.push('<small>[' + data.messages[i].time + ']</small> &nbsp; ' + data.messages[i].message);
                        contents.push('</p></div>');
                        list.push(contents.join(''));
                    }
                    list.push('</div></div></div>');
                    $('#instance_message').html(list.join(''));
                }
            } else {
                $('#instance_message').html('');
            }
        });
    }

    update_badge_function();

    if (checkEvent) {
        setInterval(update_badge_function, 60000);
    }
}