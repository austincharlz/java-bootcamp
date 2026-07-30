| Smell                                | Example in baseline                        |
|--------------------------------------|--------------------------------------------|
| Poor naming                          | `doStuff`, `data`, `a/b/c`                 |
| Raw types                            | `List data`                                |
| Long method / mixed responsibilities | create + update jammed together            |
| Stringly-typed status                | `e.equals("ACTIVE")` chains                |
| Incorrect equality                   | `==` for String IDs                        |
| Null as control flow                 | return `null` on errors                    |
| Side-effect logging                  | `System.out.println`                       |
| Magic behavior                       | name containing `"UPDATE"` triggers update |