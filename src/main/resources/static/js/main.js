const tableUsers = $('#userTable')
const editModal = $('#editModal')
const deleteModal = $('#deleteModal')
const userAddForm = $('#userAddForm');

userAddForm.find(':submit').click(() => {
    insertUser();
});

fillUsersTable()

$('#nav-users_table-link').click(() => {
    loadUsersTable();
});
$('#nav-user_form-link').click(() => {
    loadAddForm();
});

function loadUsersTable() {
    $('#nav-users_table-link').addClass('active');
    $('#nav-users_table').addClass('show').addClass('active');
    $('#nav-user_form-link').removeClass('active');
    $('#nav-user_form').removeClass('show').removeClass('active');
    fillUsersTable()
}

function loadAddForm() {
    $('#nav-user_form-link').addClass('active');
    $('#nav-user_form').addClass('show').addClass('active');
    $('#nav-users_table-link').removeClass('active');
    $('#nav-users_table').removeClass('show').removeClass('active');
    loadUserForInsertForm();
}

function loadUserForInsertForm() {

    userAddForm.find('#newfirstName').val('');
    userAddForm.find('#newlastName').val('');
    userAddForm.find('#newage').val('0');
    userAddForm.find('#newemail').val('');
    userAddForm.find('#newpassword').val('');

    fetch('/api/v1/users/roles', {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then(response => {
        userAddForm.find('#newRoles').empty();
        return response.json()
    }).then(roles => {
        const roleSelect = userAddForm.find('#newRoles')
        roles.forEach(role => {
            roleSelect.append($('<option>').val(role.id).text(role.name.replace("ROLE_", "")));
        });
    });
}

function fillUsersTable() {
    fetch('/api/v1/users', {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then(response => {
        if (response.status !== 200) {
            console.log(response.body)
            return;
        }
        return response.json()
    }).then(data => {
        tableUsers.empty()
        data.forEach(user => {
            tableUsers.append(
                '<tr id="' + user.id + '_row">' +
                '<td id="' + user.id + '_id">' + user.id + '</td>' +
                '<td id="' + user.id + '_firstName">' + user.firstName + '</td>' +
                '<td id="' + user.id + '_lastName">' + user.lastName + '</td>' +
                '<td id="' + user.id + '_age">' + user.age + '</td>' +
                '<td id="' + user.id + '_email">' + user.email + '</td>' +
                '<td id="' + user.id + '_roles">' + user.roles.map(role => role.name.replace("ROLE_", " ")) + '</td>' +
                '<td><button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#editModal" onclick="openEditModal(' + user.id + ')">Edit</button></td>' +
                '<td><button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal" onclick="openDeleteModal(' + user.id + ')">Delete</button></td>' +
                '</tr>'
            )
        })
    })
}

function openEditModal(id) {
    fetch('/api/v1/users/' + id + '/roles', {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then(response => {
        if (response.status !== 200) {
            console.log(response.body)
            return;
        }
        return response.json()
    }).then(data => {
        const user = data.user
        editModal.find('#idEdit').val(user.id)
        editModal.find('#firstNameEdit').val(user.firstName)
        editModal.find('#lastNameEdit').val(user.lastName)
        editModal.find('#ageEdit').val(user.age)
        editModal.find('#emailEdit').val(user.email)
        editModal.find('#passwordEdit').val(user.password)
        const selectRoles = editModal.find('#rolesEdit')
        selectRoles.empty()
        data.roles.forEach(role => {
            selectRoles.append($('<option>')
                .prop('selected', user.roles.filter(r => r.id === role.id).length)
                .val(role.id).text(role.name.replace("ROLE_", "")))
        })
        editModal.find('#buttonEdit').attr('onClick', 'editUser(' + user.id + ')')
        editModal.show()
    })
}

function editUser(id) {
    let user = {
        "id": parseInt(editModal.find('#idEdit').val()),
        "firstName": editModal.find('#firstNameEdit').val(),
        "lastName": editModal.find('#lastNameEdit').val(),
        "age": editModal.find('#ageEdit').val(),
        "email": editModal.find('#emailEdit').val(),
        "password": editModal.find('#passwordEdit').val(),
        "rolesId": editModal.find('#rolesEdit').val().map(roleId => parseInt(roleId))
    }

    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Accept', 'application/json');

    let request = new Request('/api/v1/users', {
        method: 'PUT',
        headers: headers,
        body: JSON.stringify(user)
    });

    fetch(request).then(response => {
        if (response.status !== 200) {
            console.log(response.body)
        }
        return response.json()
    }).then(user => {
        console.log(user)
        tableUsers.find('#' + user.id + '_firstName').text(user.firstName)
        tableUsers.find('#' + user.id + '_lastName').text(user.lastName)
        tableUsers.find('#' + user.id + '_age').text(user.age)
        tableUsers.find('#' + user.id + '_email').text(user.email)
        tableUsers.find('#' + user.id + '_roles').text(user.roles.map(role => role.name.replace("ROLE_", " ")));
        editModal.hide()
    })
}

function openDeleteModal(id) {
    fetch('/api/v1/users/' + id, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then(response => {
        if (response.status !== 200) {
            console.log(response.body)
            return;
        }
        return response.json()
    }).then(user => {
        deleteModal.find('#idDelete').val(user.id)
        deleteModal.find('#firstNameDelete').val(user.firstName)
        deleteModal.find('#lastNameDelete').val(user.lastName)
        deleteModal.find('#ageDelete').val(user.age)
        deleteModal.find('#emailDelete').val(user.email)
        deleteModal.find('#passwordDelete').val(user.password)
        const selectRoles = deleteModal.find('#rolesEdit')
        selectRoles.empty()
        user.roles.forEach(role => {
            selectRoles.append(
                '<option>' + role.name.replace("ROLE_", "") + '</option>'
            )
        })
        deleteModal.find('#buttonDelete').attr('onClick', 'deleteUser(' + user.id + ')')
        deleteModal.show()
    })
}

function deleteUser(id) {
    fetch('/api/v1/users/' + id, {
        method: 'DELETE'
    }).then(response => {
        if (response.status !== 200) {
            console.log(response.body)
        }
        tableUsers.find('#' + id + '_row').remove()
        deleteModal.hide()
    })
}

function insertUser() {
    let user = {
        'firstName': userAddForm.find('#newFirstName').val(),
        'lastName': userAddForm.find('#newLastName').val(),
        'age': userAddForm.find('#newAge').val(),
        'email': userAddForm.find('#newEmail').val(),
        'password': userAddForm.find('#newPassword').val(),
        'rolesId': userAddForm.find('#newRoles').val().map(roleId => parseInt(roleId))
    }

    let headers = new Headers();
    headers.append('Content-Type', 'application/json');

    let request = new Request('/api/v1/users', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(user)
    });

    fetch(request)
        .then(response => {
            if (response.status === 200) {
                loadUsersTable();
            }
        });
}