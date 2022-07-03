const vm = new Vue({
    el: '#root',
    data: {
        page: {
            items:null
        },
        user: null,
        cart: {
            totalCount:0,
            lastName:'',
            totalPrice:0,
            items: null
        },
        jump:'',
        msg:'',
        searchName:'',
        searchNumber:{
            min:'',
            max:''
        },
    },
    /*components: {
        Test
    },*/
    methods: {
        async jumpPage(){
            let check = /^[0-9]*[1-9][0-9]*$/
            if (!check.test(this.jump)) {
                this.msg = "别闹，输入点正常的数"
                $("#modal").modal("toggle")
            }
            else {
                let response = await axios.get(this.page.url + 'pageNo=' + this.jump).catch(() => {
                    this.msg = "通信异常"
                    $("#modal").modal("toggle")
                })
                if (response != null && response.data.result) {
                    this.page = response.data.page
                }
            }
        },
        showDetail(id){
          location.href = '/client/detail.html?id=' + id
        },
        async jumpTo(i) {
            let response = await axios.get(this.page.url + 'pageNo=' + i).catch(() => {
                this.msg = "通信异常"
                $("#modal").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.page = response.data.page
            }
        },
        async jumpToIndex() {
            let response = await axios.get(this.page.url + 'pageNo=1').catch(() => {
                this.msg = "通信异常"
                $("#modal").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.page = response.data.page
            }
        },
        async jumpToEnd() {
            let response = await axios.get(this.page.url + 'pageNo=' + this.page.pageTotal).catch(() => {
                this.msg = "通信异常"
                $("#modal").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.page = response.data.page
            }
        },
        async nextPage() {
            let a = this.page.pageNo + 1
            let response = await axios.get(this.page.url + 'pageNo=' + a).catch(() => {
                this.msg = "通信异常"
                $("#modal").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.page = response.data.page
            }
        },
        async lastPage() {
            let a = this.page.pageNo - 1
            let response = await axios.get(this.page.url + 'pageNo=' + a).catch(() => {
                this.msg = "通信异常"
                $("#modal").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.page = response.data.page
            }
        },
        async searchByPrice() {
            let check_double = /^\d+(\.\d+)?$/;
            if (this.searchNumber.min === '' && this.searchNumber.max === '') {
                let response = await axios.get('/index').catch(error => {
                    this.msg = "网络异常"
                    $("#modal").modal("toggle")
                })
                if (response != null && response.data.result) {
                    this.page = response.data.page
                }
            }
            else if (check_double.test(this.searchNumber.min) && check_double.test(this.searchNumber.max)) {
                let response = await axios.get('/index/pageByPrice?min=' + this.searchNumber.min + '&max=' + this.searchNumber.max).catch(() => {
                    this.msg = "通信异常"
                    $("#modal").modal("toggle")
                })
                if (response != null && response.data.result) {
                    this.page = response.data.page
                    if (this.page.pageTotal === 0) {
                        this.msg = "没有查询到任何数据哦"
                        $("#modal").modal("toggle")
                    }
                }
            }
            else {
                this.msg = "别闹，输入正常的数哦"
                $("#modal").modal("toggle")
            }
        },
        async searchByName() {
            let response = await axios.get('/index/pageByName?name=' + this.searchName).catch(() => {
                this.msg = "通信异常"
                $("#modal").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.page = response.data.page
                if (this.page.pageTotal === 0) {
                    this.msg = "没有查询到任何数据哦"
                    $("#modal").modal("toggle")
                }
            }
        },
        async addToCart(id) {
            let response = await axios.get('/cart/add?id='+ id).catch(() => {
                this.msg = "通信异常"
                $("#modal").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.cart = response.data.cart
            }
        },
        async logout() {
            let response = await axios.get('/logout').catch(() => {
                this.msg = "网络异常"
                $("#modal").modal("toggle")
            })
            if (response != null) {
                location.href = "/client/index.html"
            }
        },
        /*showPage(page) {
            this.page = page
        },
        showCart(cart) {
            this.cart = cart
        }*/
    },
    computed:{
        cartNumber(){
            if (this.cart.totalCount === 0)
                return '您的购物车是空的'
            else
                return '您的购物车中共有 ' + this.cart.totalCount + ' 件商品'
        },
        lastName(){
            return "您刚刚将<span style=\"color: red\">" + "《" + this.cart.lastName + "》" + "</span>添加到了购物车"
        },
        lastNameIsShow() {
            return this.cart.totalCount !== 0;
        },
    },
    async created() {
        let response = await axios.get('/index').catch(error => {
            this.msg = "网络异常"
            $("#modal").modal("toggle")
        })
        if (response != null && response.data.result) {
            this.page = response.data.page
            this.cart = response.data.cart
            // this.$bus.$emit('page', page.pageNo, page.url , page.pageTotal)
        }
        response = await axios.get('/login/isSignIn').catch(() => {
            this.msg = "网络异常"
            $("#modal").modal("toggle")
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
    /*mounted() {
        this.$bus.$on('showPage', this.showPage)
        this.$bus.$on('showCart', this.showCart)
    },
    beforeCreate() {
        Vue.prototype.$bus = this
    }*/
})