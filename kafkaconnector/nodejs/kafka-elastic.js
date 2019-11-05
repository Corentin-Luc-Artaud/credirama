var elasticsearch = require('elasticsearch');
var elastic = new elasticsearch.Client({
    host: 'elasticsearch:9200',
    log: 'info'
});

var kafka = require('kafka-node'),
    Consumer = kafka.Consumer,
    client = new kafka.KafkaClient({kafkaHost: 'kafka:9093'}),
    consumer = new Consumer(
        client,
        [
            { topic: 'client', maxNum: 1 }
        ],
        {
            groupId: 'nodejs-group',
            fromOffset: false,
            kafkaHost: "kafka:9093"
        }
    );

consumer.on('message', function (message) {
    var data = JSON.parse(message.value);
    elastic.index({
        index: 'creditrama',
        type: 'creditrama',
        body: data
    }, function (error, response) {
        if (error !== undefined) console.log(error);
    });
    console.log("Message received ", message)
});