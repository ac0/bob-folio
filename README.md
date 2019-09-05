##### NOTE: Junit 4.8.2 jar dependency
_As the zip has to go through gmail servers and they block jars, the dependency
cannot be shipped in the jar. So it needs to be either downloaded explicitly or
alternatively it gets automatically copied down by building just once using the 
pom. See Project Structure section_

### Requirement Increments
The assignment was implemented by incorporating-in the following
incremental requirements
- **R1**: Read lines from file stream as raw strings..but processing as each line is read
- **R2**: Now start interpreting each line as a wallet entry(code,amount); format xyz=8.9
- **R3**: Process each entry by assessing it's value and print against it. For 
  now, compute assessed-value = walletEntry.amount, disregarding currency
- **R4**: Sum up the assessed values and print a summary at the bottom with the 
  total; of course let the assessed value continue to be the raw amount
- **R5**: Use the crypto-compare API to implement the real value assessment in Euros
- **R6**: Now widen IO options to cover all user options and usability like spacing
- **R7**: Add documentation

### Git repository & TDD
The git repository accompanying the project is important. The above requirements are referred
 using the **Rn** code in each of the commit comments, which here serve as the primary
 documentation and is also the only way to trace the TDD as the interfaces have 
 evolved with these requirements, and especially for evolution between the series of 
 commits of a single requirement. Otherwise code comments were normally added only
 to highlight anything.

### Project Structure and Execution
A pom.xml has been included but please note that **there is NO maven dependency** itself.
 even if a pom.xml has been provided. It was added to declare and limit the dependencies(to just junit)
 and the lang versions. Then the plugins were configured to copy down the junit to under the
 project root - *target/libs/junit-4.8.2.jar*.

Therefore the code under **src/main/java** can be compiled to say *target/classes* and the main class
executed like

``$ java -cp target/classes ex.ac.PortfolioApp /tmp/cryptos.txt``

Would use the specified file as input. Without arguments:

``$ java -cp target/classes ex.ac.PortfolioApp``

will use _./bobs_crypto.txt_ as input if present or revert to stdin if not. Stdin can
also be forced by a hyphen

``$ java -cp target/classes ex.ac.PortfolioApp -``

Of course the first option is redundant as the hyphen plus redirection can achieve
the same. Output logging or result is to stdout. 

Of course executing the tests minus maven also means compiling and running using JunitRunner from under 
**src/test/java** against the code classpath and *target/libs/junit-4.8.2.jar* like

``java -cp target/libs/junit-4.8.2.jar:target/classes/:target/test-classes org.junit.runner.JUnitCore ex.ac.wallet.WalletReaderTest``


### CRLF and encoding
Or line-endings, the respective platform's native line-endings are supported. Encoding 
should not be a factor as the currency codes are effectively in the ASCII range but
the platform default charset is applied to any conversion api


### Assumtions/general notes
- The JSON parsing for the adapter is an outstanding problem. In my understanding of the
objective, with the constraints on libraries, I assumed the the intention was not to demonstrate
foolproof handparsing of the JSON for all possible combinations.
- The approach I took was to do a strict regex parse and provide an acceptability
test ``CryptoCompareExchangeAdaptorTest`` class to check the health of the interface. If the
tests in there works, the interface can be reasonably relied
- Also on the adapter, the API was designed for the functional requirement. It has
not been considered against a performance requirement.
- Same for leaving some of the interfaces too seggregated. They can be refactored
if it were not at the risk of traceability for the exercise coding
- Also I have to note that some of the key aspects of the approach doesn't really benefit
the standard scenario itself
    - First is for naturally orienting it for on-the-fly processing. It makes sense as it can run on really
    long sequences without having to buffer anything. If it's purely limited to personal inventory
    examples, it doesn't benefit from the approach
    - It made more sense to see the file as someone's record of entries/transactions rather than a table
     of coins per currency without duplicates, hence the entry model. The additional work involved having
     to maintain a stateful converter as it wouldn't make sense to use 2 different exchange rates to convert
     the 4th entry and the 27th entry if they had the same base currency
    - Further along the entry/transaction interpretation, one can see how negative value
    can be used to represent when say someone loaned out some currency. This should work if one takes out the
     intentional regex negative validation, but again not needed in the typical scenario
    - These were not latched on as requirements but aspects of the modelling and can 
    arguably help extensibility/evolution
