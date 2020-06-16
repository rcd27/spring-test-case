package com.github.rcd27.springtestcase

import com.github.rcd27.springtestcase.rabbit.receiver.Receiver
import com.github.rcd27.springtestcase.rabbit.receiver.Receiver.Companion.receiveMethodName
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

fun main(args: Array<String>) {
    runApplication<SpringTestCaseApplication>(*args)
}

@SpringBootApplication
class SpringTestCaseApplication {
    companion object {
        const val queueName = "rabbit-queue"
        const val topicExchangeName = "emailValidation" // or not?
        const val messageRoutingKey = "foo.bar.#"
    }

    /**
     * The message listener container and receiver beans are all you need to listen for messages.
     * To send a message, you also need a Rabbit template.
     *
     * Seems like queue(), exchange() and binding() are additional
     */
    @Bean fun queue(): Queue {
        return Queue(queueName, false)
    }

    @Bean fun exchange(): TopicExchange {
        return TopicExchange(topicExchangeName)
    }

    @Bean fun binding(queue: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(messageRoutingKey)
    }

    /**
     * Must-have for receiving messages
     */
    @Bean fun container(connectionFactory: ConnectionFactory,
                        listenerAdapter: MessageListenerAdapter
    ): SimpleMessageListenerContainer {

        return SimpleMessageListenerContainer().apply {
            setConnectionFactory(connectionFactory)
            setQueueNames(queueName)
            setMessageListener(listenerAdapter)
        }
    }

    /**
     * [Receiver] is registered here to listen for the messages.
     * It listens for the messages on the spring-boot queue
     */
    @Bean fun listenerAdapter(receiver: Receiver): MessageListenerAdapter {
        return MessageListenerAdapter(receiver, receiveMethodName)
    }
}