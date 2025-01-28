// URL endpoint of posts API
// and helper methods to make HTTP API Requests such as
// POST, PUT, DELETE, GET

class PostApi {
    constructor() {
        this.baseUrl = '/api/v1/posts'
    }

    async findAll() {
        const response = await fetch(this.baseUrl); // method=GET
        const data = await response.json();
        console.log(data);
        return data;
    }

    async findById(id) {
        const response = await fetch(`${this.baseUrl}/${id}`); // method=GET
        // const response1 = await fetch(this.baseUrl+"/"+id); // method=GET
        const data = await response.json();
        console.log(data);
        return data;
    }

    async add(post) {
        const response = await fetch(this.baseUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(post)
        });
        const data = await response.json();
        console.log(data);
        return data;
    }

    async modify(id, post) {
        const response = await fetch(this.baseUrl + "/" + id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(post)
        });
        const data = await response.json();
        console.log(data);
        return data;
    }

    async removeById(id) {
        const response = await fetch(this.baseUrl + "/" + id, {
            method: 'DELETE'
        });
        const data = await response.text();
        console.log(data);
        return data;
    }
}






