This repository is a small test project for [lift/framework#1569][ticket]. It does little
more than invoke the Lift mailer at an address you request and send some emails.

To give it a run, clone the repo then do the following:

1. Do a `publish-local` of the framework code on the pull request above.
1. Pop open default.props and replace the SMTP information with the information you
   need for your SMTP relay.
2. On your console type `sbt run` and give it an email address at the prompt.

[ticket]: http://github.com/lift/framework/pulls/1569
