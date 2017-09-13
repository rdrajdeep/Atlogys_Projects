Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal
Scenario: scenario description

When login with {"email": "kishor+expert31@atlogys.com","password": "jyoti1032" }

Then expert should be able to post expert profile as
{
    "tags": [
       1,2,3,4
        ],
    "medias":[],
    "headline": "Java-8 Lambda",
    "summary": "Java Expert",
    "my_experience": "In test automation",
    "year_of_experience":"3",
    "educational_background": "Bachelor of Technology (Computer Science and Information Engineering)"
}

Then get profile

Then update information on expert profile as
{
"my_experience": "In test java"
}

Then get profile

Then upload media as C:\Users\Kishor\Desktop\fp.png

Then delete the profile


