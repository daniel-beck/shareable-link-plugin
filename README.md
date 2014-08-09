Shareable Link Plugin
=====================

Plugin that provides shareable links (with views and other request-specific URL elements removed).

This link is available in the footer and transforms URLs like

    http://localhost:8080/jenkins/user/danielbeck/my-views/view/All/job/j/build?delay=0sec

into request/view independent URLs like

    http://configuredhostname:8080/jenkins/job/j/build?delay=0sec
