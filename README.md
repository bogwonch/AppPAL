# AppPAL

AppPAL (pronounced *apple*) is an authorization logic based on *SecPAL* for Android.

## Grammar

    VARIABLE: UPPER TOKENCHAR*
    CONSTANT: '"' [^"]+ '"'
    e: VARIABLE #variable
     | CONSTANT #constant
     
    
    ZERO: '0'
    INF: 'inf'
    d: ZERO #zero
     | INF  #inf
     
    
    PREDICATE: LOWER TOKENCHAR*

    PREDICATE ∈ { isInstallable
                , isMetBy 
                }

    vp: PREDICATE ('(' e (',' e)* ')')? #predicate
      | 'can-say' d fact                #canSay
      | 'can-act-as' e                  #canActAs
    
    fact: e vp
    
    claim: fact ('if' (fact (',' fact)* ))? ('where' c)?
    
    assertion: e 'says' claim '.'
    
    ac: assertion+
    
    f: PREDICATE
    f ∈ { hasPermission
        , lacksPermission
        , containsPackage
        , containsNativeCode
        , isSignedBy
        , isProvenBy
        , hasRatingGoogle
        }

    ce: e
      | f '(' ( ce (',' ce)* )? ')'
      | 'true'
      | 'false'

    c: 'sat'
     | '!' c
     | ce '=' ce
     | c ',' c


Valid verb-phrases include:

 *  (e : App) isInstallable
 *  (e : Policy) isMetBy (a : App)


Valid constraint-functions:

 *  hasPermission(a : App, p : String) : Bool
 *  hasPackage(a : App, p : String) : Bool
 *  hasNativeCode(a : App) : Bool
 *  isSignedBy(a : App, p : String) : Bool
 *  isProvenBy(a : App, e : Policy, c : String) : Bool
 *  hasRatingGoogle(a : App) : Int(5)




