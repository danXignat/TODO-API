from pydantic import BaseModel

class User(BaseModel):
    username: str
    hashPassword: str
    firstName: str
    lastName: str

class Project(BaseModel):
    projectName: str
    description: str = ""
    startDate: str = ""
    endDate: str = ""
    userId: str = ""