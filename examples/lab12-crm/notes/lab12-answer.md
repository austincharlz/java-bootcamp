#### Implementation Checkpoints
Checkpoint A
- Pass
- Pass
- Pass
Checkpoint B
- Pass
- Pass
- Pass
- Pass
Checkpoint C
- Pass
- Pass
- Pass
Checkpoint D
- Pass
- Pass
- Pass
- Pass
#### Reflection Questions
1. Replacing List with a HashMap eliminated the "==" bug. The old implementation could not reliably find customers or detect duplicates. 
2. All 8 unit tests pas unchanged which validates create, get, updateStatus, etc. Main demo also succeeded without any issues or bugs. 
3. The "UPDATE" magic: checking if the name contains "UPDATE" to trigger status change. Hard to justify because someone might claim "it's a feature"—but it violates separation of concerns, hides behavior in an unrelated parameter, and breaks discoverability.