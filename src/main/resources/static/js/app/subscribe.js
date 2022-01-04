function connect_websocket(basedir) {
    var socket = new SockJS(basedir + 'websocket');
    var stompClient = Stomp.over(socket);

    var callback = function(notification) {
        $('#websocket-message').html(JSON.parse(notification.body).content);
        $('#websocket-message-div').show();
    }
    stompClient.debug = null;
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/user/queue/notify', callback);
        stompClient.subscribe('/topic/greetings', callback);
    });
}