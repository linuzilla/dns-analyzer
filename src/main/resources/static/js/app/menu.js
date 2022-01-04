function NcuBootstrapMenu() {
}

NcuBootstrapMenu.prototype.load_submenu = function(entry, level) {
    var list = new Array();

    list.push('<li class="dropdown">');
    list.push('<a class="dropdown-toggle" data-toggle="dropdown" href="' + entry.urlpath + '" id="' + this.menu_id(entry) + '">');
    list.push(entry.message);
    if (level == 1) {
        list.push(' &nbsp;<span class="badge" id="' + this.badge_id(entry) + '"></span>')
    }
    list.push(' <span class="caret"></span></a>');
    list.push('<ul class="dropdown-menu" aria-labelledby="themes">');
    for (var i = 0; i < entry.subentries.length; i++) {
        list.push(this.load_menu_item(entry.subentries[i], level + 1));
    }
    list.push('</ul>');
    list.push('</li>');
    return list.join('');
}

NcuBootstrapMenu.prototype.load_menu_entry = function(entry) {
    if (entry.subentries != null) {
        return this.load_submenu(entry, 1);
    } else {
        return this.load_menu_item(entry, 1);
    }
}

NcuBootstrapMenu.prototype.load_menu_item = function(entry, level) {
    var list = new Array();
    list.push('<li><a href="' + entry.urlpath + '" id="'+ this.menu_id(entry) + '">')
    list.push(entry.message);
    if (level == 1) {
        list.push(' &nbsp;<span class="badge" id="' + this.badge_id(entry) + '"></span>')
    }
    list.push('</a></li>');
    return list.join('');
}

NcuBootstrapMenu.prototype.badge_id = function(entry) {
    return "badge-" + entry.messageKey.replace(/\./g, '-');
}

NcuBootstrapMenu.prototype.menu_id = function(entry) {
    return entry.messageKey.replace(/\./g, '-');
}

NcuBootstrapMenu.prototype.load_menu = function(backendUrl, callback) {
    var self = this;

    ncuCommonLib.get(backendUrl, function (err, response) {
        if (err == null) {
            var results = new Array();

            for (var i = 0; i < response.length; i++) {
                results.push(self.load_menu_entry(response[i]));
            }

            $("#main-menu").html(results.join(''));
            callback(null);
        } else {
            callback(err);
        }
    });
}
