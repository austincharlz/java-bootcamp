#### Implementation Checkpoints 
Checkpoint A
1. Pass
2. Pass
3. Pass
Checkpoint B
1. Pass
2. Pass
3. Pass
Checkpoint C
1. Pass
2. Pass
3. Pass
Checkpoint D
1. Pass
2. Pass
3. Pass
#### Reflection Questions
1. The biggest choice was storing customers in a simple shared in-memory list/map and making all requests go through that one service. That made the app return the right customer data every time and behave the way the lab expected when a customer was missing.
2. The best proof is that the automated tests passed twice and the app responded correctly when checked through the browser-style endpoints. Health showed the app was up, customer creation worked, customer lookup worked, and a missing customer gave the expected error.
3. The trickiest problem was when the info endpoint was there but showed no useful details. It looked like it should work, but it needed one extra setting before the app name and description would appear.