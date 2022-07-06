package com.vijay.chatter.Model

class Users {
    var profilePic: String? = null
    var userName: String? = null
    var email: String? = null
    var password: String? = null
    var uid: String? = null
    var lastMessage: String? = null
    var status: String? = null


    constructor(
        _profilePic: String,
        _username: String,
        _email: String,
        _password: String,
        _uid: String,
        _lastMessage: String,
        _status: String
    ) {
        this.profilePic = _profilePic
        this.userName = _username
        this.email = _email
        this.password = _password
        this.uid = _uid
        this.lastMessage = _lastMessage
        this.status = _status
    }

    constructor(_username: String? = null, _email: String? = null, _password: String? = null, _uid: String?= null) {
        this.userName = _username
        this.email = _email
        this.password = _password
        this.uid = _uid
    }
    constructor()
}
