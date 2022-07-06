package com.vijay.chatter.Model

class MessageModel {
    var uid: String? = ""
    var message: String? = ""
    var messageId: String? = ""
    var time:String? = ""

    constructor()

    constructor(_uid: String, _message:String)
    {
        this.uid = _uid
        this.message = _message
    }

    constructor(_uid: String, _message: String, _time:String)
    {
        this.uid = _uid
        this.message = _message
        this.time = _time
    }

}