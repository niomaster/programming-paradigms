import Data.Char
import qualified Data.List
import FPPrac.Trees

-- FP - Series 1 --

f :: Integer -> Integer
f x = 2*x*x + 3*x - 5

code :: Int -> Char -> Char
code n c | c >= 'a' && c <= 'z' = chr ((ord c + n - ord 'a') `mod` 26 + ord 'a')
         | c >= 'A' && c <= 'Z' = chr ((ord c + n - ord 'A') `mod` 26 + ord 'A')
         | otherwise            = c

interest :: Double -> Double -> Integer -> Double
interest a r 0 = a
interest a r n = (1.0 + r) * interest a r (n-1)

discr :: Double -> Double -> Double -> Double
discr a b c = b^2  - 4*a*c

root1 :: Double -> Double -> Double -> Double
root1 a b c = if d > 0 then (-b + (sqrt d)) / (2*a) else error "negative discriminant"
    where d = discr a b c

root2 :: Double -> Double -> Double -> Double
root2 a b c = if d > 0 then (-b - (sqrt d)) / (2*a) else error "negative discriminant"
    where d = discr a b c

extrX :: Double -> Double -> Double -> Double
extrX a b c = -b / (2*a)

extrY :: Double -> Double -> Double -> Double
extrY a b c = a * x^2 + b * x + c
    where x = extrX a b c

length' :: [a] -> Integer
length' [] = 0
length' (x:xs) = 1 + length' xs

sum' :: Num a => [a] -> a
sum' [] = 0
sum' (x:xs) = x + sum' xs

reverse' :: [a] -> [a]
reverse' [] = []
reverse' (x:xs) = reverse' xs ++ [x]

take' :: Int -> [a] -> [a]
take' 0 xs = []
take' n [] = []
take' n (x:xs) = x : take' (n-1) xs

elem' :: Eq a => a -> [a] -> Bool
elem' a [] = False
elem' a (x:xs) = (x == a) || elem' a xs

concat' :: [[a]] -> [a]
concat' [] = []
concat' (x:xs) = x ++ concat' xs

maximum' :: Ord a => [a] -> a
maximum' [] = error "Cannot determine the maximum of an empty list"
maximum' [x] = x
maximum' (x:xs) = max x $ maximum' xs

zip' :: [a] -> [b] -> [(a, b)]
zip' [] bs = []
zip' as [] = []
zip' (a:as) (b:bs) = (a, b) : zip' as bs

r :: Num a => a -> a -> [a]
r a d = a : r (a + d) d

r1 :: Num a => a -> a -> Int -> a
r1 a d n = r a d !! n

total :: Num a => a -> a -> Int -> Int -> a
total a d i j = sum $ take (j - i + 1) $ drop i $ r a d

allEqual :: Eq a => [a] -> Bool
allEqual [] = True
allEqual [x] = True
allEqual (x:x':xs) = x == x' && allEqual (x':xs)

diffs :: Num a => [a] -> [a]
diffs [] = []
diffs [x] = []
diffs (x:x':xs) = (x' - x) : diffs (x':xs)

isAS :: (Num a, Eq a) => [a] -> Bool
isAS xs = allEqual $ diffs xs

allRowsEquallyLong :: Num a => [[a]] -> Bool
allRowsEquallyLong m = allEqual $ map (length) m

totals :: Num a => [[a]] -> [a]
totals m = map (sum) m

transpose :: Num a => [[a]] -> [[a]]
transpose m | sum (map (length) m) == 0 = []
            | allRowsEquallyLong m      = map (head) m : transpose (map (tail) m)
            | otherwise                 = error "Cannot transpose a matrix with unbalanced rows" 

totalsCols :: Num a => [[a]] -> [a]
totalsCols m = totals $ transpose m

-- FP - Series 2 --

filter' :: (a -> Bool) -> [a] -> [a]
filter' f [] = []
filter' f (x:xs) = if (f x) then x : filter' f xs else filter' f xs

foldl' :: (a -> b -> a) -> a -> [b] -> a
foldl' f z [] = z
foldl' f z (x:xs) = foldl' f (f z x) xs

foldr' :: (a -> b -> b) -> b -> [a] -> b
foldr' f z [] = z
foldr' f z (x:xs) = f x $ foldr' f z xs

zipWith' :: (a -> b -> c) -> [a] -> [b] -> [c]
zipWith' f [] bs = []
zipWith' f as [] = []
zipWith' f (a:as) (b:bs) = (f a b) : zipWith' f as bs

--2a: [([Char], Num, Char, [Char])]

getName :: Num a => ([Char], a, Char, [Char]) -> [Char]
getName (name, age, sex, place) = name

setName :: Num a => [Char] -> ([Char], a, Char, [Char]) -> ([Char], a, Char, [Char])
setName name' (name, age, sex, place) = (name', age, sex, place)

getAge :: Num a => ([Char], a, Char, [Char]) -> a
getAge (name, age, sex, place) = age

setAge :: Num a => a -> ([Char], a, Char, [Char]) -> ([Char], a, Char, [Char])
setAge age' (name, age, sex, place) = (name, age', sex, place)

getSex :: Num a => ([Char], a, Char, [Char]) -> Char
getSex (name, age, sex, place) = sex

setSex :: Num a => Char -> ([Char], a, Char, [Char]) -> ([Char], a, Char, [Char])
setSex sex' (name, age, sex, place) = (name, age, sex', place)

getPlace :: Num a => ([Char], a, Char, [Char]) -> [Char]
getPlace (name, age, sex, place) = place

setPlace :: Num a => [Char] -> ([Char], a, Char, [Char]) -> ([Char], a, Char, [Char])
setPlace place' (name, age, sex, place) = (name, age, sex, place')

increaseAges :: (Num a) => a -> [([Char], a, Char, [Char])] -> [([Char], a, Char, [Char])]
--increaseAges n ps = map (\p -> setAge (n + getAge p) p) ps

increaseAges n ps = [ setAge (n + getAge p) p | p <- ps ]

--increaseAges n [] = []
--increaseAges n (p:ps) = setAge (n + getAge p) p : increaseAges n ps

women :: (Num a, Ord a) => [([Char], a, Char, [Char])] -> [[Char]]
--women ps = map getName $ filter (\p -> (getSex p) == 'F' && 30 <= (getAge p) && (getAge p) < 40) ps

--women ps = [ getName p | p <- ps, (getSex p) == 'F' && 30 <= (getAge p) && (getAge p) < 40 ]

women [] = []
women (p:ps) | (getSex p) == 'F' && 30 <= (getAge p) && (getAge p) < 40 = (getName p) : rest
             | otherwise = rest
    where rest = women ps

getAgeByName :: (Num a) => [Char] -> [([Char], a, Char, [Char])] -> Maybe a
getAgeByName name [] = Nothing
getAgeByName name (p:ps) = if (getName p) == name then Just (getAge p) else getAgeByName name ps

sortByAge :: (Num a, Ord a) => [([Char], a, Char, [Char])] -> [([Char], a, Char, [Char])]
sortByAge ps = map snd $ Data.List.sort $ map (\p -> (getAge p, p)) ps

sieve :: [Integer] -> [Integer]
sieve (p:ps) = p : filter (\x -> x `mod` p /= 0) (sieve ps)

primes :: [Integer]
primes = sieve [2..]

isPrime :: Integer -> Bool
isPrime n = n `elem` (takeWhile (<=n) primes)

firstPrimes :: Int -> [Integer]
firstPrimes n = take n primes

smallPrimes :: Integer -> [Integer]
smallPrimes n = takeWhile (<n) primes

dividers :: Integral a => a -> [a]
dividers n = filter ((==0) . (n `mod`)) [1..n]

altIsPrime :: Integral a => a -> Bool
altIsPrime n = (dividers n) == [1, n]

pyth :: Integer -> [(Integer, Integer, Integer)]
pyth n = [ (a, b, c) | a <- [1..n], b <- [1..n], c <- [1..n], a^2 + b^2 == c^2 ]

--gcd :: Integer -> Integer -> Integer
--gcd a 0 = a
--gcd 0 b = b
--gcd a b | a < b       = gcd (b % a) a
--      | otherwise     = gcd b a

pyth' :: Integer -> [(Integer, Integer, Integer)]
pyth' n = [ (a, b, c) | a <- [1..n], b <- [1..n], c <- [1..n], gcd a b == 1, gcd b c == 1, a <= b, a^2 + b^2 == c^2 ]

increasing :: Ord a => [a] -> Bool
increasing [] = True
increasing [x] = True
increasing (x:x':xs) = (x < x') && increasing (x':xs)

weaklyIncreasingP :: (Num a, Ord a) => a -> a -> [a] -> Bool
weaklyIncreasingP total n [] = True
weaklyIncreasingP total n [x] = True
weaklyIncreasingP total n (x:xs) = (n == 0 || x * n > total) && weaklyIncreasingP (total + x) (n + 1) xs

weaklyIncreasing :: (Num a, Ord a) => [a] -> Bool
weaklyIncreasing = weaklyIncreasingP 0 0

sublist :: (Eq a) => [a] -> [a] -> Bool
sublist [] ys = True
sublist xs [] = False
sublist xs ys = and (zipWith (==) xs ys) || sublist xs (tail ys)

partialSublist :: (Eq a) => [a] -> [a] -> Bool
partialSublist [] ys = True
partialSublist xs [] = False
partialSublist (x:xs) (y:ys) | x == y       = partialSublist xs ys
                             | otherwise    = partialSublist (x:xs) ys

bubble :: (Ord a) => [a] -> [a]
bubble [] = []
bubble [x] = [x]
bubble (x:x':xs) | x < x'       = x : bubble (x':xs)
                 | otherwise    = x' : bubble (x:xs)

bsort :: (Ord a) => [a] -> [a]
bsort [] = []
bsort xs = y : bsort ys 
         where (y:ys) = bubble xs

mmsort :: (Ord a) => [a] -> [a]
mmsort [] = []
mmsort [x] = [x]
mmsort xs = [mn] ++ mmsort (xs Data.List.\\ [mn, mx]) ++ [mx]
          where mn = minimum xs
                mx = maximum xs

ins :: (Ord a) => a -> [a] -> [a]
ins i [] = [i]
ins i (x:xs) | i < x        = (i:x:xs)
             | otherwise    = x : (ins i xs)

isort :: (Ord a) => [a] -> [a]
isort = foldr (ins) []

merge :: (Ord a) => [a] -> [a] -> [a]
merge xs [] = xs
merge [] ys = ys
merge (x:xs) (y:ys) | x < y         = x : merge xs (y:ys)
                    | otherwise     = y : merge (x:xs) ys

msort :: (Ord a) => [a] -> [a]
msort [] = []
msort [x] = [x]
msort xs = merge (msort $ take half xs) (msort $ drop half xs)
         where half = (length xs) `div` 2

qsort :: (Ord a) => [a] -> [a]
qsort [] = []
qsort (x:xs) = (qsort $ filter (<x) xs) ++ [x] ++ (qsort $ filter (>=x) xs)

-- FP - Series 3 --

data Tree1a = Leaf1a Int
            | Node1a Int Tree1a Tree1a

pp1a :: Tree1a -> RoseTree
pp1a (Leaf1a x) = RoseNode (show x) []
pp1a (Node1a x left right) = RoseNode (show x) [pp1a left, pp1a right]

data Tree1b = Leaf1b (Int, Int)
            | Node1b (Int, Int) Tree1b Tree1b

pp1b :: Tree1b -> RoseTree
pp1b (Leaf1b d) = RoseNode (show d) []
pp1b (Node1b d left right) = RoseNode (show d) [pp1b left, pp1b right]

data Tree1c = Leaf1c Int
            | Node1c Tree1c Tree1c

pp1c :: Tree1c -> RoseTree
pp1c (Leaf1c i) = RoseNode (show i) []
pp1c (Node1c left right) = RoseNode "" [pp1c left, pp1c right]

data Tree1d = Leaf1d (Int, Int)
            | Node1d [Tree1d]

pp1d :: Tree1d -> RoseTree
pp1d (Leaf1d d) = RoseNode (show d) []
pp1d (Node1d subtrees) = RoseNode "" (map pp1d subtrees)

mapTree :: (Int -> Int) -> Tree1a -> Tree1a
mapTree f (Leaf1a i) = Leaf1a (f i)
mapTree f (Node1a i left right) = Node1a (f i) (mapTree f left) (mapTree f right)

treeAdd :: Int -> Tree1a -> Tree1a
treeAdd inc = mapTree (+inc)

treeSquare :: Tree1a -> Tree1a
treeSquare = mapTree (^2)

mapTreeTuple :: ((Int, Int) -> Int) -> Tree1b -> Tree1a
mapTreeTuple f (Leaf1b d) = Leaf1a (f d)
mapTreeTuple f (Node1b d left right) = Node1a (f d) (mapTreeTuple f left) (mapTreeTuple f right)

addNode :: Tree1b -> Tree1a
addNode = mapTreeTuple $ uncurry (+)

binMirror :: Tree1a -> Tree1a
binMirror (Leaf1a i) = Leaf1a i
binMirror (Node1a i left right) = Node1a i right left

mirror :: Tree1d -> Tree1d
mirror (Leaf1d (a, b)) = Leaf1d (b, a)
mirror (Node1d subtrees) = Node1d $ reverse $ map mirror subtrees

data Tree = Leaf
          | Node Int Tree Tree deriving Show

insertTree :: Int -> Tree -> Tree
insertTree i Leaf = Node i Leaf Leaf
insertTree i (Node val left right) | i < val        = Node val (insertTree i left) right
                                   | otherwise      = Node val left (insertTree i right)

makeTree :: [Int] -> Tree
makeTree = foldr insertTree Leaf

makeList :: Tree -> [Int]
makeList Leaf = []
makeList (Node i left right) = (makeList left) ++ [i] ++ (makeList right)

treeSort :: [Int] -> [Int]
treeSort = makeList . makeTree

sortTree :: Tree -> Tree
sortTree = makeTree . makeList

subtreeAt :: Int -> Tree -> Maybe Tree
subtreeAt i Leaf = Nothing
subtreeAt i (Node val left right) | (i == val)      = Just (Node val left right)
                                  | (i < val)       = subtreeAt i left
                                  | otherwise       = subtreeAt i right

cutOffAt :: Int -> Tree -> Tree
cutOffAt 0 t = Leaf
cutOffAt i Leaf = Leaf
cutOffAt i (Node val left right) = Node val (cutOffAt (i-1) left) (cutOffAt (i-1) right)

replace :: Int -> String -> Tree -> Tree
replace i s Leaf = Leaf -- Do not replace, since we cannot follow the path to a node.
replace i "" (Node val left right) = Node i left right
replace i ('r':s) (Node val left right) = Node val left (replace i s right)
replace i ('l':s) (Node val left right) = Node val (replace i s left) right

subTree :: String -> Tree -> Maybe Tree
subTree "" t = Just t
subTree s Leaf = Nothing
subTree ('l':s) (Node val left right) = subTree s left
subTree ('r':s) (Node val left right) = subTree s right

shortestBranch :: Tree -> Int
shortestBranch Leaf = 0
shortestBranch (Node val left right) = 1 + min (shortestBranch left) (shortestBranch right)

longestBranch :: Tree -> Int
longestBranch Leaf = 0
longestBranch (Node val left right) = 1 + max (longestBranch left) (longestBranch right)

isBalanced :: Tree -> Bool
isBalanced t = abs (shortestBranch t - longestBranch t) <= 1

balanceList :: [Int] -> Tree
balanceList [] = Leaf
balanceList xs = Node (head right) (balanceList left) (balanceList $ tail right)
           where left = take half xs
                 right = drop half xs
                 half = (length xs) `div` 2

balance :: Tree -> Tree
balance t = balanceList $ makeList t

-- FP - Series 3 --

data BinTree a b = BinNode a (BinTree a b) (BinTree a b)
                 | BinLeaf b

data Unit = Unit

type Tree1a' = BinTree Int Int
type Tree1b' = BinTree (Int, Int) (Int, Int)
type Tree1c' = BinTree Unit Int

pp :: (Show a, Show b) => BinTree a b -> RoseTree
pp (BinNode d left right) = RoseNode (show d) [pp left, pp right]
pp (BinLeaf d) = RoseNode (show d) []

--variant 1

number :: Integer -> (BinTree String Integer)
number i = BinLeaf i

op :: (BinTree String Integer) -> String -> (BinTree String Integer) -> (BinTree String Integer)
op left o right = BinNode o left right

expect :: Char -> (String, f) -> (String, f)
expect exp ((c:cs), f) | exp == c       = (cs, f)
                       | otherwise      = error ("Parse error, expected " ++ [c])

parseInteger :: (String, (BinTree String Integer) -> a) -> (String, a)
parseInteger (('0':s), f) = (s, f $ BinLeaf 0)
parseInteger (('1':s), f) = (s, f $ BinLeaf 1)
parseInteger (('2':s), f) = (s, f $ BinLeaf 2)
parseInteger (('3':s), f) = (s, f $ BinLeaf 3)
parseInteger (('4':s), f) = (s, f $ BinLeaf 4)
parseInteger (('5':s), f) = (s, f $ BinLeaf 5)
parseInteger (('6':s), f) = (s, f $ BinLeaf 6)
parseInteger (('7':s), f) = (s, f $ BinLeaf 7)
parseInteger (('8':s), f) = (s, f $ BinLeaf 8)
parseInteger (('9':s), f) = (s, f $ BinLeaf 9)

parseOp :: (String, String -> a) -> (String, a)
parseOp (('+':s), f) = (s, f "+")
parseOp (('-':s), f) = (s, f "-")
parseOp (('*':s), f) = (s, f "*")
parseOp (('/':s), f) = (s, f "/")

parseExpr :: (String, (BinTree String Integer) -> a) -> (String, a)
parseExpr (('(':s), f) = f $ expect ')' $ parseExpr (s, op)
parseExpr (s, f) = f $ parseExpr $ parseOp $ parseInteger (s, op)

parse :: String -> BinTree String Integer
parse s = snd $ parseExpr (s, \x -> x)