package com.example

import io.micronaut.context.event.BeanCreatedEvent
import io.micronaut.context.event.BeanCreatedEventListener
import io.micronaut.http.netty.channel.ChannelPipelineCustomizer
import javax.inject.Singleton

@Singleton
class ChannelCustomizer : BeanCreatedEventListener<ChannelPipelineCustomizer> {
    var clientChannelInvoked = false

    override fun onCreated(event: BeanCreatedEvent<ChannelPipelineCustomizer>): ChannelPipelineCustomizer {
        if (event.bean.isClientChannel) {
            clientChannelInvoked = true
        }
        return event.bean
    }
}
