document.addEventListener('DOMContentLoaded', function() {
    const userList = document.getElementById('user-list');
    const userForm = document.getElementById('user-form');

    // Fetch all users and display them
    function fetchUsers() {
        fetch('/api/users')
            .then(response => response.json())
            .then(data => {
                userList.innerHTML = '';
                data.forEach(user => {
                    const li = document.createElement('li');
                    li.textContent = `${user.name} (${user.email})`;
                    userList.appendChild(li);
                });
            })
            .catch(error => console.error('Error fetching users:', error));
    }

    // Handle form submission to create a new user
    userForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const formData = new FormData(userForm);
        const user = {
            name: formData.get('name'),
            email: formData.get('email')
        };

        fetch('/api/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        })
        .then(response => {
            if (response.ok) {
                fetchUsers(); // Refresh the user list
                userForm.reset(); // Clear the form
            } else {
                console.error('Error creating user:', response.statusText);
            }
        })
        .catch(error => console.error('Error:', error));
    });

    // Initial fetch of users
    fetchUsers();
});