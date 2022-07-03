new Vue({
    el: '#root',
    data: {
        book: {
            name:'',
            author:'',
            price:'',
            sales:'',
            stock:'',
            imgPath:''
        },
        url:'',
        msg:''
    },
    methods: {
        async submitBook() {
            let check = /^[0-9]*[0-9][0-9]*$/
            let check_double = /^\d+(\.\d+)?$/;
            let file = this.$refs.fileInput.files[0]
            if (this.book.name !== '' && this.book.author !== '' && this.book.price !== '' && this.book.sales !== '' && this.book.stock !== '' && (file != null || this.book.imgPath !== '')) {
                if (check.test(this.book.stock) && check_double.test(this.book.price) && check.test(this.book.sales)) {
                    let form = new FormData()
                    form.append('name', this.book.name)
                    form.append('author', this.book.author)
                    form.append('price', this.book.price)
                    form.append('sales', this.book.sales)
                    form.append('stock', this.book.stock)
                    if (file != null) form.append('file', file, file.name)
                    else if (this.book.imgPath !== '') form.append('imgPath', this.book.imgPath)
                    form.append('id', this.url)
                    debugger
                    let response = await axios.post('/manager/book/edit', form, {
                        headers: {ContentType: 'multipart/form-data'}
                    }).catch(() => {
                        this.msg = "网络异常"
                        $("#modal1").modal("toggle")
                    })
                    if (response != null && response.data.result) {
                        this.book = response.data.book
                        if (this.url !== 'new') {
                            this.msg = "修改成功"
                        }
                        else
                            this.msg = "添加成功"
                        $("#modal1").modal("toggle")
                    }
                }
                else {
                    this.msg = "别闹，检查输入是否正确哦"
                    $("#modal1").modal("toggle")
                }
            }
            else {
                this.msg = "请输入完整的信息哦"
                $("#modal1").modal("toggle")
            }
        },
        reset() {
            this.book.name = ''
            this.book.price = ''
            this.book.author = ''
            this.book.sales = ''
            this.book.stock = ''
            this.$refs.fileInput.value = null
        }
    },
    async created() {
        this.url = location.search
        this.url = this.url.slice(this.url.indexOf("=")+1)
        if (this.url !== 'new') {
            let response = await axios.get('/manager/book/detail?id=' + this.url).catch(error => {
                this.msg = "网络异常"
                $("#modal1").modal("toggle")
            })
            if (response != null && response.data.result) {
                this.book = response.data.book
            }
            else {
                this.msg = response.data.msg
                $("#modal1").modal("toggle")
            }
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