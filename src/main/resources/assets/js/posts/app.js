// STARTIMI - apo inicializimi i programit
// lidhja e DOM me variablat ne JS

// const postApi = new PostApi();
//
// postApi.findById(3)
//     .then(data => console.log("Loaded successfully!"))
//     .catch(error => console.error("Error loading data!"))
//     .finally(() => console.log("I am done!"));
//
// console.log("CKA BONE NJERI!");

class PostApp {
    constructor() {
        this.postApi = new PostApi();
        this.posts = [];
        this.postsTBody = document.getElementById("posts");
        this.loaderDiv = document.getElementById("loader");
        this.mainTable = document.querySelector("div.table-responsive");
        this.body = document.querySelector("body");
        this.formPost = document.getElementById("formPost");
        this.btnRefresh = document.getElementById("btnRefresh");
    }

    async init() {

        this.formPost.addEventListener("submit", async(event) =>{
            event.preventDefault();
            console.log(event)
            console.log(event.target)

            // Create a FormData object from the form
            const formData = new FormData(event.target);

            // Get the comment from the FormData object
            const comment = formData.get("comment");
            // Optionally, convert FormData to a plain JavaScript object
            const formDataObject = {};
            formData.forEach((value, key) => {
                formDataObject[key] = value;
            });

            console.log(formDataObject);
            const post = {
                id:0,
                userId:formDataObject.userId,
                content: comment,
                imageUrl:'',
                videoUrl: '',
                accessible: true,
                createdAt: new Date(),
                modifiedAt: null
            };

            await this.postApi.add(post);
            await this.loadPosts();
            event.target.reset();

        });

        this.btnRefresh.addEventListener("click",(event)=>{
            this.loadPosts();
        })

        this.loadPosts();

        this.postsTBody.addEventListener("click", async (event) => {
            console.log(event);
            console.log(event.target)
            if (event.target.tagName === "BUTTON" && event.target.innerText === 'DELETE') {

                if (!confirm(`Are you sure you want to delete post with id ${event.target.dataset.id}?`)) {
                    return;
                }

                // let id = event.target.parentElement.parentElement.firstElementChild.textContent;
                // console.log(id)
                console.log(event.target.dataset.id)
                const id = event.target.dataset.id;
                await this.postApi.removeById(id);
                await this.loadPosts();

            }
        });
    }

    async loadPosts() {
        this.mainTable.style = {display: "none"};
        this.loaderDiv.style.display = "block";

        this.posts = await this.postApi.findAll();
        this.displayPosts();

        this.mainTable.style = {display: "block"}
        this.loaderDiv.style.display = "none";
        // setTimeout(() => {
        //     this.loaderDiv.style = "display:none";
        //     this.mainTable.style = "display:block";
        // }, 2000)
    }

    displayPosts() {
        this.postsTBody.innerHTML = '';

        this.posts.forEach(post => {
            this.postsTBody.innerHTML += `
                <tr>
                    <td>${post.id}</td>
                    <td>${post.userId}</td>
                    <td>${post.content}</td>
                    <td>${post.createdAt}</td>
                    <td>
                        <button class="btn btn-danger" type="button" data-id="${post.id}"
                        data-accessible="${post.accessible}">DELETE</button>
                    </td>
                </tr>
            `;
        });
    }
}

new PostApp().init()
    .then(() => console.log("Application initialized successfully!"))
    .catch(error => console.error("Error initializing application!"))
















