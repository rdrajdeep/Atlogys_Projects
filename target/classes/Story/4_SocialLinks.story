Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: scenario description

When login with {"email": "kishor+expert31@atlogys.com","password": "jyoti1032" }

Then Create a new Profile as {
                                 "tags": [
                                     ],
                                  "medias":[],
                                 "headline": "Java-8 Lambda",
                                 "summary": "Java Expert",
                                 "my_experience": "In test automation",
                                 "year_of_experience":"3",
                                 "educational_background": "Bachelor of Technology (Computer Science and Information Engineering)"
                             }

When add {"url": "https://staff.tumblr.com/rss/"} as RSS Feed


When add {"url": "http://feeds.feedburner.com/ndtvnews-world-news"} as RSS Feed


When add facebook to social Link

When add instagram to social Link

When add youtube to social Link

Then get the social links

Then count of social links should be 2

Then add social link to expert profile

When list all social links of a ExpertProfile

Then count of social links should be 2

When remove one social link

Then get the social links

Then  count of social links should be 1

Then list all social links of a ExpertProfile

Then  count of social links should be 1

