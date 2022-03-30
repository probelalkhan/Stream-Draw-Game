package io.getstream.utils

import io.getstream.chat.android.client.channel.ChannelClient
import io.getstream.chat.android.client.models.Channel

inline val String.channelId
    get() = "$CHANNEL_MESSAGING:$this"

inline val String.groupName
    get() = "$this's Group"

inline val Channel.groupId: String
    get() = cid.split(":")[1]

inline val ChannelClient.groupId: String
    get() = cid.split(":")[1]

inline val Channel.hostName: String?
    get() = this.extraData[KEY_HOST_NAME]?.toString()

inline val Channel.selectedWord: String?
    get() = this.extraData[KEY_SELECTED_WORD]?.toString()

