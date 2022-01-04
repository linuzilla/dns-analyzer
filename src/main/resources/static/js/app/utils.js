function timestampToDateString(timestamp) {
     var date = new Date(timestamp);

     var year = date.getFullYear();
     var month = "0" + (date.getMonth() + 1);
     var day = "0" + date.getDate();
     var hours = "0" + date.getHours();
     var minutes = "0" + date.getMinutes();
     var seconds = "0" + date.getSeconds();

     return year + "-" + month.substr(-2) + "-" + day.substr(-2) + " " + hours.substr(-2) + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
 }