import requests
import json
import bcrypt

from models import *

class APIManager:
    def __init__(self):
        self.base_url = "http://127.0.0.1:3001/api/v1"
        self.token = None

    # Authentication endpoints
    def login(self, username, password):
        print(username, password)
        response = requests.post(
            f"{self.base_url}/users/auth",
            json={"username": username, "hashPassword": password}
        )

        if response.status_code == 200:
            return response.json()
        else:
            print("invalid credentials")

    def register(self, user: User):
        url = f"{self.base_url}/users"
        print(url)
        try:
            response = requests.post(
                url,
                json=user.dict()
            )
            # return self._handle_response(response) is not None, response.text

            return True, "succes"
        except Exception as e:
            return False, str(e)

    # Todo endpoints
    def get_todos(self, id: str):
        """Get all todos for current user"""
        headers = {"Authorization": f"Bearer {self.token}"}
        response = requests.get(
            f"{self.base_url}/projects/user/{id}",
            headers=headers
        )
        return response.json()

    def add_todo(self, task, id):
        """Add new todo"""

        model = Project(projectName = task)
        print(id)
        print(model)
        response = requests.post(
            f"{self.base_url}/projects?userId={id}",
            json=model.dict()
        )

        print(response)

    def update_todo(self, todo_id, task):
        """Update existing todo"""
        headers = {"Authorization": f"Bearer {self.token}"}
        response = requests.put(
            f"{self.base_url}/todos/{todo_id}",
            headers=headers,
            json={"task": task}
        )
        return self._handle_response(response)

    def delete_todo(self, todo_id):
        """Delete todo"""
        headers = {"Authorization": f"Bearer {self.token}"}
        response = requests.delete(
            f"{self.base_url}/todos/{todo_id}",
            headers=headers
        )
        return self._handle_response(response)

    def logout(self):
        """Clear stored token"""
        self.token = None