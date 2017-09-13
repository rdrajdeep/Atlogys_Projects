Meta:

When register expert with {"email": "kishor+expert31@atlogys.com","password": "1032kishor" } as user2
Then check success code 2001

Then Resend email verification instructions for {"email": "kishor+expert31@atlogys.com"}
Then check success code 2041
Then Verify Email
Then check success code 2042

Then login with user2

Then Change password to jyoti1032 for user2
Then check success code 2021
Then Reset password to jyoti1032 for user2

Then login with {"email": "kishor+expert30@atlogys.com","password": "jyoti1032" }


