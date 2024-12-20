const { ref, onMounted, createApp } = Vue;

const WS_SERVER_PORT = 5001;

createApp({
  setup() {
    const ws = new WebSocket(`ws://localhost:${WS_SERVER_PORT}/websocket`);

    const recipientId = ref("");
    const content = ref("");
    const chat = ref([]);

    function sendMessage() {
      const message = {
        recipientId: recipientId.value,
        content: content.value,
      };
      ws.send(JSON.stringify(message));
      recipientId.value = "";
      content.value = "";
    }

    function receiveMessage(message) {
      const data = JSON.parse(message.data);
      chat.value.push(data);
      console.log(data);
    }

    function connect(ws, event) {
      console.log("Connected");
    }

    onMounted(() => {
      ws.onopen = connect;
      ws.onmessage = receiveMessage;
      ws.onclose = () => console.log("WebSocket closed");
      ws.onerror = (err) => console.error(err);
    });

    return {
      recipientId,
      content,
      chat,
      sendMessage,
    };
  },
}).mount("#app");
