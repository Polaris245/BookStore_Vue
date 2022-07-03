new Vue({
    el: '#root',
    data: {
        book: {
            name:'',
            author:'',
            price:'',
            stock:'',
            sales:'',
            imgPath:''
        },
        url:'',
        user:null,
        msg:''
    },
    methods: {
        async addToCart() {
            let response = await axios.get('/cart/add?id='+ this.url).catch(() => {
                this.msg = "通信异常"
                $("#modal").modal("toggle")
            })
            if (response != null && response.data.result) {
                location.href = "/client/cart.html"
            }
        },
    },
    async created() {
        this.url = location.search
        this.url = this.url.slice(this.url.indexOf("=") + 1)
        let response = await axios.get('/book/detail?id=' + this.url)
        if (response != null && response.data.result) {
            this.book = response.data.book
        }
        response = await axios.get('/login/isSignIn')
        if (response != null && response.data.result) {
            this.user = response.data.user
        }
    },
    mounted() {
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
    }
})