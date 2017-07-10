- Predict the mood of the user based on the notification
- Ring happy tone if the message will induce happy emotion
- Else ring sad tone

# Check it out at: Google Play Store

Build during Engineering Hackathon - University of Waterloo, October 2016
# Inspiration

After a very long day, we just hate to hear that mono-ringtone. Then the idea of a dynamic notification tone came across our mind. Why not let the phone app processes the notification and predicts whether the user wants to read this message or not?
# What it does

The application triggers an Android background service, which runs even if the app is closed. The background service intercepts custom or all notification from the phone. Depending on the mood of the message, the service will notify the user dynamically. If you hear a bad notification tone, then the message is probably not pleasant to read. Where as if you hear a positive tone, then maybe it is a girl asking you out for dinner.

#Challenges we ran into
How to predict whether the user wants to read this message or not? To achieve this, we used some online Sentiment predicting API and accumulating the user's message history. The more messages the app intercepts, the more accurate the feedback.
#Accomplishments that we’re proud of

Certainly, the user gets an accurate feedback on the mood of the message. The app runs seamlessly asking for permission when necessary and enabling the user to disable service upon request. The algorithm adjusts to user and will fit to the user in a short amount of time.
#What we learned

Android SDK is very powerful, providing countless features and challenges. Reading through the documentation and trying to figure out how to use the built-in feature, are hard to manage in the beginning.
#What’s next for Emotify

Emotify will continue to explore more feature that pre-filters notifications for users. Features such as text to speech on positive message will be shortly launched.
