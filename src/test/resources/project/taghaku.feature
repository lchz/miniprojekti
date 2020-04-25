Feature: User can search tips by tag

    Scenario: when searching by tag user will be redirected to the search result page
        Given a reading tip with title "Youtube", author "Google", description "videos", url "www.youtube.com" and tags "video" is added
        When search form is filled with name "video"
        And submit tag search
        Then user is redirected to the right page

    Scenario: result page contains right results when searching by tag
        Given a reading tip with title "The Guardian", author "the guardian", description "news articles", url "www.theguardian.com" and tags "article" is added
        When search form is filled with name "article"
        And submit tag search
        Then results contain tip with title "The Guardian" and tag "article"
        And there is "1" row in the search results