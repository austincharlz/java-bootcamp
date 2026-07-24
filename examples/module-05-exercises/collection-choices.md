# Collection choices

| # | Scenario | Need (order / unique / key→value / sorted) | Interface            | Implementation | Why                                 |
| - | -------- |--------------------------------------------|----------------------|----------------|-------------------------------------|
| 1 | Ordered catalog; duplicate titles allowed | order                                      | `List<Book>`         | `ArrayList<>`  | Indexed sequence; duplicates OK     |
| 2 | Unique registered book IDs | unique                                     | `Set<String>`        | `HashSet<>`    | No duplicates; fast membership      |
| 3 | Book ID → current borrower ID | key -> value                               | `Map<String, String>` | `HashMap<>`    | Direct key -> value lookup          |
| 4 | Alphabetically sorted categories | sorted, unique                             | `Set<String>`        | `TreeSet<>`    | Unique values; natural sort order   |
| 5 | Category → count, sorted by category | key -> value, sorted                       | `Map<String, Integer>` | `TreeMap<>`    | Key -> value with sorted keys       |
| 6 | Checkout history in event order | order                                      | `List<BorrowedRecord` | `ArrayList<>`  | Append + iterate in insertion order |

#### Answers:
1. If unique IDs must also preserve registration order, what changes?
   - LinkedHashSet<String> instead of a HashSet<String>. Elements remain unique while preserving the insertion order. 
2. If borrower lookup must preserve insertion order for display, what changes?
   - Use a LinkedHashMap<String, String> instead of a HashMap<String, String> because it stores key -> value pairs while order is maintained (via insertion).
3. If many insertions/removals occur in the middle, is LinkedList automatically best?
   4. No, access pattern and traversal cost matter; measure rather than assume. 