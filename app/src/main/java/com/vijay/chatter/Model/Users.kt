package com.vijay.chatter.Model

class Users {
    var profilePic: String = ""
    var userName: String = ""
    var email: String = ""
    var password: String = ""
    var uid: String = ""
    var lastMessage: String = ""
    var status: String = ""

    constructor(_profilePic:String, _username: String, _email: String, _password: String, _uid:String, _lastMessage:String, _status:String){
        this.profilePic = _profilePic
        this.userName = _username
        this.email = _email
        this.password = _password
        this.uid = _uid
        this.lastMessage = _lastMessage
        this.status = _status
    }

    constructor(_username:String, _email:String, _password:String){
        this.userName = _username
        this.email = _email
        this.password = _password
    }



}