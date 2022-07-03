new Vue({
    el: '#root',
    data: {
        signIn:{
            username:'',
            password:''
        },
        signUp:{
            username:'',
            password:'',
            repassword:'',
            email:'',
            code:''
        },
        msg:'',
    },
    methods: {
        async signInSub(){
            let usernamePatt = /^\w{5,12}$/;
            if (this.signIn.username === "") {
                this.msg = "用户名不能为空"
            }
            else if (!usernamePatt.test(this.signIn.username)) {
                this.msg = "请输入5-12位字母或数字"
            } else {
                let resp = await axios.post('/login/signIn', {
                    username: this.signIn.username,
                    password: this.signIn.password
                }).catch(error =>{
                    this.msg = "请求失败，通信异常"
                })
                if (resp == null)
                    return
                if (resp.data.result) {
                    location.href = "/client/index.html"
                }
                else {
                    this.msg = "用户名或密码错误"
                }
            }
        },
        async signUpSub(){
            let usernamePatt = /^\w{5,12}$/;
            let emailPatt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
            if (!usernamePatt.test(this.signUp.username)) {
                this.msg = "用户名不合法"
                return
            }
            else {
                let resp = await axios.get('/login/existUser?username=' + this.signUp.username).catch(error => {
                    this.msg = "请求失败，通信异常"
                })
                if (resp === null)
                    return
                if (resp.data.result) {
                    this.msg = "用户名已存在"
                    return
                }
            }
            if (!usernamePatt.test(this.signUp.password)) {
                this.msg = "密码不合法"
                return
            }
            if (this.signUp.password !== this.signUp.repassword) {
                this.msg = "两次输入的密码不一致"
                return
            }
            if (!emailPatt.test(this.signUp.email)) {
                this.msg = "邮箱格式不正确"
                return
            }
            if (this.signUp.code === "") {
                this.msg = "请输入验证码"
            }
            else {
                let resp = await axios.get('/login/codeVerify?code=' + this.signUp.code).catch(error => {
                    this.msg = "请求失败，通信异常"
                })
                if (resp === null)
                    return
                if (resp.data.result) {
                    let response = await axios.post('/login/signUp', {
                        username: this.signUp.username,
                        password: this.signUp.password,
                        email: this.signUp.email,
                        code: this.signUp.code
                    }).catch(error => {
                        this.msg = "请求失败，通信异常"
                    })
                    if (response.data.result)
                        location.href = "/client/index.html"
                    else
                        this.msg = response.data.msg
                }
                else {
                    this.msg = resp.data.msg
                }
            }
        },
        flushCode(e){
            e.target.src = "/login/kaptcha?d=" + new Date().getTime()
        },
        clearMsg(){
            this.msg = ""
        },
        async isLegal(e){
            let name = e.target.name
            let value = e.target.value
            let usernamePatt = /^\w{5,12}$/;
            let emailPatt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
            switch (name){
                case 'username':
                    if (value === "")
                        this.msg ="用户名不能为空"
                    else if (!usernamePatt.test(value))
                        this.msg = "用户名不合法"
                    else {
                        let resp = await axios.get('/login/existUser?username=' + this.signUp.username).catch(error => {
                            this.msg = "请求失败，通信异常"
                        })
                        if (resp === null)
                            return
                        if (resp.data.result) {
                            this.msg = "用户名已存在"
                        }
                        else
                            this.msg = "用户名可用"
                    }
                    break
                case 'password':
                    if (value === "")
                        this.msg ="密码不能为空"
                    else if (!usernamePatt.test(value))
                        this.msg = "密码不合法"
                    break
                case 'repassword':
                    if (value !== this.signUp.password)
                        this.msg = "两次输入的密码不一致"
                    else
                        this.msg = ""
                    break
                case 'email':
                    if (value === "")
                        this.msg ="邮箱不能为空"
                    else if (!emailPatt.test(value))
                        this.msg = "邮箱格式不合法"
                    break
                case 'code':
                    if (value === "")
                        this.msg ="验证码不能为空"
            }
        }
    },
})

// 获取要操作的元素
let login_title=document.querySelector('.login-title');
let register_title=document.querySelector('.register-title');
let login_box=document.querySelector('.login-box');
let register_box=document.querySelector('.register-box');

// 绑定标题点击事件
login_title.addEventListener('click',()=>{
    // 判断是否收起，收起才可以点击
    if(login_box.classList.contains('slide-up')){
        register_box.classList.add('slide-up');
        login_box.classList.remove('slide-up');
    }
})
register_title.addEventListener('click',()=>{
    if(register_box.classList.contains('slide-up')){
        login_box.classList.add('slide-up');
        register_box.classList.remove('slide-up');
    }
})