new Vue({
    el: '#root',
    data: {
        items:null,
        msg: '',
        url: '',
        user: null,
        status:0
    },
    methods: {
        async logout() {
            let response = await axios.get('/logout').catch(() => {
                this.msg = "网络异常"
                $("#modal1").modal("toggle")
            })
            if (response != null) {
                location.href = "/client/index.html"
            }
        },
    },
    async created() {
        this.url = location.search
        this.url = this.url.slice(this.url.indexOf("=")+1)
        console.log(this.url)
        let response = await axios.get('/order/detail?id=' + this.url).catch(error => {
            this.msg = "网络异常"
            $("#modal1").modal("toggle")
        })
        if (response != null && response.data.result) {
            this.items = response.data.orderItems
            this.status = response.data.status
        }
        else {
            this.msg = response.data.msg
            $("#modal1").modal("toggle")
        }
        response = await axios.get('/login/isSignIn').catch(() => {
            this.msg = "网络异常"
            $("#modal1").modal("toggle")
        })
        if (response != null && response.data.result) {
            this.user = response.data.user
        }
    },
    updated() {
        const links = document.querySelectorAll('.nav li a');
        links.forEach(link => {
            let letters = link.textContent.split('');
            link.textContent = '';
            letters.forEach((letter, i) => {
                let span = document.createElement('span');
                span.textContent = letter;
                span.dataset.text = letter;
                span.style.transitionDelay = i / 15 + 's';
                link.append(span);
            })
        })
    }
})