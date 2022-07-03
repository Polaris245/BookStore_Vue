new Vue({
    el: '#root',
    data: {
        user: {
            username:'',
            password:'',
            email:'',
            admin:false
        },
        msg: '',
        temp: false
    },
    computed: {
      role() {
          return this.user.admin ? "管理员" : "普通用户"
      }
    },
    methods: {
        changeInfo() {
            let usernamePatt = /^\w{5,12}$/
            let emailPatt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/
            if (usernamePatt.test(this.user.password) && emailPatt.test(this.user.email)) {
                $("#modal2").modal("toggle")
            }
            else {
                this.msg = "数据错误"
                $("#modal1").modal("toggle")
            }
        },
        async sub() {
            $("#modal2").modal("hide")
            let form = new FormData()
            form.append("password", this.user.password)
            form.append("username", this.user.username)
            form.append("email", this.user.email)
            let response = await axios.post('/user/update', form
            ).catch(error => {
                this.msg = "网络异常"
                $("#modal1").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.msg = "更新成功，请重新登陆"
                $("#modal1").modal("toggle")
                this.temp = true
            }
        },
        jump() {
            if (this.temp) {
                location.href = "/client/index.html"
                this.temp = false
            }
        },
        async logout() {
            let response = await axios.get('/logout').catch(() => {
                this.msg = "网络异常"
                $("#modal1").modal("toggle")
            })
            if (response != null) {
                location.href = "/client/index.html"
            }
        }
    },
    async created() {
        let response = await axios.get('/user/userinfo').catch(error => {
            this.msg = "网络异常"
            $("#modal1").modal("toggle")
        })
        if (response != null && response.data.result) {
            this.user = response.data.user
        }
    },
    updated() {
        const links=document.querySelectorAll('.nav li a');
        links.forEach(link=>{
            let letters=link.textContent.split('');
            link.textContent='';
            letters.forEach((letter,i)=>{
                let span=document.createElement('span');
                span.textContent=letter;
                span.dataset.text=letter;
                span.style.transitionDelay=i/15+'s';
                link.append(span);
            })
        })
    },
})