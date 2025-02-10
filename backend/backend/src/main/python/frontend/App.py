import customtkinter as ctk
from tkinter import messagebox

import bcrypt
from APIManager import APIManager
from models import *

class TodoApp:
    def __init__(self):
        self.app = ctk.CTk()
        self.app.title("Todo Application")
        self.app.geometry("800x600")

        # Initialize API manager
        self.api = APIManager()
        self.salt = bcrypt.gensalt()
        # Start with login frame
        self.current_user = None
        self.id = None
        self.show_login_frame()

    def show_login_frame(self):
        for widget in self.app.winfo_children():
            widget.destroy()

        frame = ctk.CTkFrame(self.app)
        frame.pack(pady=20, padx=20, fill="both", expand=True)
        ctk.CTkLabel(frame, text="Login", font=("Helvetica", 24)).pack(pady=12, padx=10)

        self.username_entry = ctk.CTkEntry(frame, placeholder_text="Username")
        self.username_entry.pack(pady=12, padx=10)

        self.password_entry = ctk.CTkEntry(frame, placeholder_text="Password", show="*")
        self.password_entry.pack(pady=12, padx=10)

        ctk.CTkButton(frame, text="Login", command=self.login).pack(pady=12, padx=10)
        ctk.CTkButton(frame, text="Register", command=self.show_register_frame).pack(pady=12, padx=10)

    def show_register_frame(self):
        for widget in self.app.winfo_children():
            widget.destroy()

        frame = ctk.CTkFrame(self.app)
        frame.pack(pady=20, padx=20, fill="both", expand=True)

        ctk.CTkLabel(frame, text="Register", font=("Helvetica", 24)).pack(pady=12, padx=10)

        self.reg_username_entry = ctk.CTkEntry(frame, placeholder_text="Username")
        self.reg_username_entry.pack(pady=12, padx=10)

        self.first_name_entry = ctk.CTkEntry(frame, placeholder_text="First name")
        self.first_name_entry.pack(pady=12, padx=10)

        self.last_name_entry = ctk.CTkEntry(frame, placeholder_text="Last name")
        self.last_name_entry.pack(pady=12, padx=10)

        self.reg_password_entry = ctk.CTkEntry(frame, placeholder_text="Password", show="*")
        self.reg_password_entry.pack(pady=12, padx=10)

        self.reg_confirm_password_entry = ctk.CTkEntry(frame, placeholder_text="Confirm Password", show="*")
        self.reg_confirm_password_entry.pack(pady=12, padx=10)

        ctk.CTkButton(frame, text="Register", command=self.register).pack(pady=12, padx=10)
        ctk.CTkButton(frame, text="Back to Login", command=self.show_login_frame).pack(pady=12, padx=10)

    def show_todo_frame(self):
        # Clear window
        for widget in self.app.winfo_children():
            widget.destroy()

        # Create todo frame
        frame = ctk.CTkFrame(self.app)
        frame.pack(pady=20, padx=20, fill="both", expand=True)

        ctk.CTkLabel(frame, text=f"Welcome {self.current_user}", font=("Helvetica", 24)).pack(pady=12, padx=10)

        # Todo entry
        self.todo_entry = ctk.CTkEntry(frame, placeholder_text="Enter new task")
        self.todo_entry.pack(pady=12, padx=10)

        # Add todo button
        ctk.CTkButton(frame, text="Add Task", command=self.add_todo).pack(pady=12, padx=10)

        # Todos list frame
        self.todos_frame = ctk.CTkFrame(frame)
        self.todos_frame.pack(pady=12, padx=10, fill="both", expand=True)

        # Logout button
        ctk.CTkButton(frame, text="Logout", command=self.logout).pack(pady=12, padx=10)

        # Display todos
        self.display_todos()

    def display_todos(self):
        # Clear todos frame
        for widget in self.todos_frame.winfo_children():
            widget.destroy()

        # Get todos from API
        todos = self.api.get_todos(self.id)

        for todo in todos:
            print(todo)

            todo_frame = ctk.CTkFrame(self.todos_frame)
            todo_frame.pack(pady=5, padx=10, fill="x")

            # Todo text
            todo_entry = ctk.CTkEntry(todo_frame)
            todo_entry.pack(side="left", padx=5)
            todo_entry.insert(0, todo['projectName'])

            # Update button
            ctk.CTkButton(
                todo_frame,
                text="Update",
                command=lambda t=todo, e=todo_entry: self.update_todo(t['id'], e.get())
            ).pack(side="left", padx=5)

            # Delete button
            ctk.CTkButton(
                todo_frame,
                text="Delete",
                command=lambda t=todo: self.delete_todo(t['projectName'])
            ).pack(side="left", padx=5)

    def login(self):
        username = self.username_entry.get()
        password = self.password_entry.get()

        data = self.api.login(username, password)
        print(data)

        self.current_user = data["username"]
        self.id = data["userId"]
        print(self.id)
        self.show_todo_frame()

    def register(self):
        username = self.reg_username_entry.get()
        password = self.reg_password_entry.get()
        confirm_password = self.reg_confirm_password_entry.get()
        first_name = self.first_name_entry.get()
        last_name = self.last_name_entry.get()
        hashed_password = password

        if not username or not password or not first_name or not last_name:
            messagebox.showerror("Error", "Please fill all fields")
            return

        if password != confirm_password:
            messagebox.showerror("Error", "Passwords do not match")
            return

        model = User(
            username = username,
            hashPassword= hashed_password,
            firstName = first_name,
            lastName = last_name
        )

        print(model.json())

        success, message = self.api.register(model)
        if success:
            messagebox.showinfo("Success", "Registration successful")
            self.show_login_frame()
        else:
            messagebox.showerror("Error", message)

    def add_todo(self):
        task = self.todo_entry.get()
        if task:
            print(task)
            self.api.add_todo(task, self.id)
            self.todo_entry.delete(0, 'end')
            self.display_todos()

    def update_todo(self, todo_id, new_task):
        if new_task:
            if self.api.update_todo(todo_id, new_task):
                self.display_todos()
            else:
                messagebox.showerror("Error", "Failed to update task")

    def delete_todo(self, todo_id):
        if self.api.delete_todo(todo_id):
            self.display_todos()
        else:
            messagebox.showerror("Error", "Failed to delete task")

    def logout(self):
        self.api.logout()
        self.current_user = None
        self.show_login_frame()

    def run(self):
        self.app.mainloop()