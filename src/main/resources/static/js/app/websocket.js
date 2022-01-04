function NcuWebSocket(basedir) {
    this.basedir = basedir;
    this.stompClient = null;
}

NcuWebSocket.prototype.connect = function(callback) {
    var socket = new SockJS(this.basedir + 'websocket');
    var self = this;

    self.stompClient = Stomp.over(socket);

    self.stompClient.debug = null; // function () {}

    self.stompClient.connect({}, function (frame) {
        // setConnected(true);
        self.stompClient.subscribe('/user/queue/notify', callback);
    });
}

NcuWebSocket.prototype.disconnect = function() {
    if (this.stompClient !== null) {
        this.stompClient.disconnect();
    }
    // setConnected(false);
    console.log("Disconnected");
}

//NcuWebSocket.prototype.sendName = function () {
//    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
// }

//NcuWebSocket.prototype.showGreeting = function (message) {
//    $("#greetings").append("<tr><td>" + message + "</td></tr>");
//}