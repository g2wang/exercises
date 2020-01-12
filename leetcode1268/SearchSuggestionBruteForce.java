/**
1268. Search Suggestions System
Medium

Given an array of strings products and a string searchWord. We want to design a system that suggests at most three product names from products after each character of searchWord is typed. Suggested products should have common prefix with the searchWord. If there are more than three products with a common prefix return the three lexicographically minimums products.

Return list of lists of the suggested products after each character of searchWord is typed. 

Example 1:

Input: products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
Output: [
["mobile","moneypot","monitor"],
["mobile","moneypot","monitor"],
["mouse","mousepad"],
["mouse","mousepad"],
["mouse","mousepad"]
]
Explanation: products sorted lexicographically = ["mobile","moneypot","monitor","mouse","mousepad"]
After typing m and mo all products match and we show user ["mobile","moneypot","monitor"]
After typing mou, mous and mouse the system suggests ["mouse","mousepad"]

Example 2:

Input: products = ["havana"], searchWord = "havana"
Output: [["havana"],["havana"],["havana"],["havana"],["havana"],["havana"]]
Example 3:

Input: products = ["bags","baggage","banner","box","cloths"], searchWord = "bags"
Output: [["baggage","bags","banner"],["baggage","bags","banner"],["baggage","bags"],["bags"]]
Example 4:

Input: products = ["havana"], searchWord = "tatiana"
Output: [[],[],[],[],[],[],[]]
 
Constraints:

1 <= products.length <= 1000
There are no repeated elements in products.
1 <= Σ products[i].length <= 2 * 10^4
All characters of products[i] are lower-case English letters.
1 <= searchWord.length <= 1000
All characters of searchWord are lower-case English letters. 
 */

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayDeque;

public class SearchSuggestionBruteForce {

    public static void main(String[] args) {
        String[] products = new String[]{
            "mobile","mouse","moneypot","monitor","mousepad"
        };
        String searchWord = "mouse";

//        String[] products = new String[]{"bags","baggage","banner","box","cloths"};
//        String searchWord = "bags";
//        String[] products = new String[]{
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltqtkzgdvpwsnzvvhgelwqqtdruknthexbnznixtpktmxlxadnnjuzynvmcajkcopefw",
//"e",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltqtkzgdvpwsnzvvhgelwqqtdruknthfmpgekmnsnxbjtgkorweyotixwlcwyvjhsgjmcxkthszqffcuanqe",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytrpopmqtztvx",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxqhqxshczwndevtdwdugkhjm",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltqtkzgdvpwsnzvvttiblsecbwbjiavybqjbubhqsosblxhjlazg",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltqtkzgdvpwsnzvvhgelwqqtdruknthfmpgekmnsnxbjtgkorweyotixwlcwyvjhsgjmcxkthszqffcuanghx",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbcrmqovcdmysuiwudydsgndjtflykmzfvoawkkargexytjuihnomiqezyqujqalafxxcg",
//"jhljdwmzajzhbzakqaljdrmlykjmnuxobohfrtkkfomnncrtyhnrmnktddhctwbmjdrbyewjtxlmvwwonjmurxatshntdvdmkyqmhsnjvykrydssnf",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltcjueumbdovisfkvoiaefajnbyqrvddajklehucxgvwgwltqwrwtljigyazlxgmlubt",
//"jyutgoofzhfhohyrtjnggsyboyfvvseghifejwcdmvzlnbvqoitmikuayaqjf",
//"crkvwptcqpxixdxswlnbzqvegbkcrzyvqyfpivuqyvkwctpqxsvjzmdkxwkxrydzwdavrydzsenajijzxzruziraagmydnhukkpscglmgkjnslerwjwjiousltlcxpjqutgqwrfrzgyzdaxssjxqjvtqupmiqmjebsmwtkv",
//"sawxfkvjanqhwadpiftppejzh",
//"jhljdprevkydjfzuynjkmaxnljentqvjxxmkguoaxpefhss",
//"mrklrbtfxyyxkmreglnvsprvpcqeiabavruobchukroohjupgdfcid",
//"tnhgepglkwouzzzuirpzblbiqiupywjoenrzgtneawycsvrmnsnthtpixftlvrhjfoohkcjttpdnmtergskgcywrnkhqbdclj",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtoqqwlufsrshrqebujsztrldskcxywiiunzpvqztigbhsnf",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltqtkzgdvpwsnzvvhgelwqqtdruknthfmpgekmnsnxbjtgkorweyotixwlcwyvjhsgjmcxofu",
//"jemqslzvrbskvqvjwbivkxiwvzunyvyydicmnujacrzbomugingscfodfroyatsdwfbbktajyvbcwxusitnbbupjrjniemerrlujvfkbvzuzyysrunmwyftywgpybacbgvprybhhbymnc",
//"jhljdwmzajbejfyewkvhnpbenzedarvjstpywnqsrwbzrwsdiazferiliucjutqsriviycqgcycqgptbvxjpdmcszurlxynvjvlpmcsjvvuhwdwggxpkisfyjpgmunydqnkgdyvcycdcaeeqngkpqbuylneelpkmrtytqt",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzmdgvq",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfglaovojaskdmlzqdcpljyogaghonmtqlmkheawtgjfjjfxwepamg",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltqtkzgdvpwsnzvvhgelwqqtdruknthfmpgekmnsnxbjtgkorweyotixwlcwyvjhsgjmcxktgckdmdknsghw",
//"jhljdwmzajzhrugyiyhfobthuolttcxhbabkgztyewftchcgnbdlfklinalsnbisnbauhavkklbstnljdabxjxrlfpzcwdvwqbuelrnpxwfyxkbsuwqhhavxjilscwsejepgeagioyuygvzeaosdydemiuzlxrrymgiipmhlrroacigbirotx",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltqtkzgdvpwsnzvvhgelwqqtdruneluwssmtwondnulmnmcric",
//"swtiutizdsgfqoouqpqnfnymxozrkmifylc",
//"qjkacgudpntqmnfjwajlvwflwujmzgumbmwnyxmidpxhjvbofutovdrxvsibvivvcpvahdlnvdpyrvkmqbodehmaos",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltqtkhrfhyuqwrlprwdrztwncnfawbhmhizugfbykzjlmkorprcpmvaqnxollxjduplrpbpkoc",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjylhkiabzfarxcdfmnazomofjjovszxwsajkgjnnqgqhxbzfzkczmqdargeklvqphngmf",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltqtkzgdmglxthgqgkfdxwbhglifxgddbudyvzbstnzajtzxrwenpgxbbgnoopjlqmvksbrblqgpoau",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltqtkzgdvpwsnzvvhgelwmgeapqviocqlwxhwdrspdnvfwsueyflaqyugnaiaudyebelavrmcinpegwdkvasq",
//"gmdipfdbrbpxtoguoczzylbvtwvkeizjzpkitxemlbpntgabfquceexutahhhtazajrvyebswsfcdmyzrbcuhbbomvfbigueneoxboixhmgadagvesvohhxikxpsgepomzrmgopjwcunqzweycsfzonuxjvseetopcwkqrxj",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltqtkzgdvpwsnzvvhgelwqqtdruknthfmpgekymqswxnblzzdrsx",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtyinanuoqxhrzpid",
//"wmdynsfqafmwakfzgmmjv",
//"geukdsnoidgngoaeutlenbeacbkwvfsnaxpjzwyfaaxtgpuagsyihiiukpcqruacvpztqpfzjkqedrgawldrwsvoeckpbaxiewvsxgwfrvxwjporwmsdzrfgxsvhqtshdpiwugahibdxwapqgct",
//"ksnkokylbndlrspqntlwkvzggnltumwgietvlntdtycnlfwtonlulrvkbwwalcbphlfyuklwaxepyykq",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltqtkzgdvpwsnzvvhgelwqqtdrrsydhaggrspsxqxjyamfzdjijwkkzlilzetrbhoufyjtvt",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyskqujjuoyergkhjiuetyuqmxlbrhvqccdojfuvxapxxmttcvkyhpwhnswbn",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzzgvnyvifeoconimcrvivomvcbxndlagodcreyjsujuujdtlggyqju",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvjslklhoywcnkynwmpcsxqmffltmxerhqhftrggsxbdjywazmbcipemgiuge",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltqtkzgdvpwsnzvvhgelwqqtdruknthfmpgekmnsnxbjtgkorweyotixwlcwyvjhahpb",
//"pgfelbqsbxstdvbwzwclelbcfuskheuguwevadhynw",
//"mzkgksixdpjpjucbwcosbutjhqxbzrajlprvfyufivhsifkhqxzhskosssvazinljvqncpbibothtrmfkfrtxjgqvml",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytylppbjczpsuzbiyuddxnxcjumqrmswhtkssqxdbmjyqpznmtb",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoewiednmizszpecznfjvzgpkzmfkkavirmvzcosdzbtvlfbwbwwedxgpqcniqf",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltqtkzgdvpwsnzvvhgelwqqtdruknthfmpgekmnsnxbjtgkorweyotixwlcwyvjhsgjmcxkthszqffcurmze",
//"kidvqciatjrmmwmgufyjdnfiznohewcegbdcklsrtxlrjpskyyihjydbsmacyrbjgihxbwruhuzlzgqmwyqosvgrtzelkcwkfettntncocivaavgnyodrshepkpbjy",
//"znzriimpzgugozuixpdsqyxcuqsuwwwenchyemscgjwmchlctkbtkdjcbkgswfwxr",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltqtkhaeyrgeellxwuzdaa",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgiclqgtjennjvmb",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprrziwvgzuwpysmnkfo",
//"jhljdwmzajzhbzlnuvnzlypvdwelcnexkdfskoxkymborxnexhyctvminpdvdekwmokprwgskobsianemxeneuovowebrusncqutzhujgkswiovoialiqokiwkmbybbjbeenxarwoxbupustfxqgpivsawecebesdyfvsknvlnt",
//"jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfafumkcmonskgyuaffuitlxqxeubifkawlkqfntkfxxjomjuffcdprwzwzpltnxaritqqdtnmuhndokxnphlhdygvfcifg",
//"jhljdwmzajzhbzlnuvnzlypvdwvlwblyfwchpsytymysowjgldscaabrrvzelklbcllnmpersjfezqmhhphq"
//        };
//        String searchWord = "jhljdwmzajzhbzlnuvnzlypvdwelzenkmuekbfhhwrfgltwxtytyeifurnnvdjyzwzsqyprcqxatrfvfabwoeoybqfbtkgicltqtkzgdvpwsnzvvhgelwqqtdruknthfmpgekmnsnxbjtgkorweyotixwlcwyvjhsgjmcxkthszqffcuanggmjxvzykcfajvlbromoiaabgtihdkyxrfdlofvhsbdjlbyktpawxdwqgwlaxqjzdvvvvrksuhfuyjimkuiptxbkehzvgefavleaegbopivdhzpzhgehjorevmxvzivdigmldsrgtlptdorekere";
//
        SearchSuggestionBruteForce ss = new SearchSuggestionBruteForce();
        List<List<String>> ans = ss.suggestedProducts(products, searchWord);
        System.out.println("[");
        for (int i = 0; i < ans.size(); i++) {
            List<String> l = ans.get(i);
            System.out.printf("%s%s\n", Arrays.toString(l.toArray(new String[]{})),
                    i == ans.size()-1?"":","); 
        }
        System.out.println("]");
    }

    private static final int MAX_PER_SEARCH = 3;

    /**
     * Strangely, this brute force method (7ms) can acutally win over theoretically correct algorithms;
     * The problem is not well designed.
     */
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);
        List<List<String>> ans = new ArrayList<>(searchWord.length());
        for (int i = 0; i < searchWord.length(); i++) {
            ans.add(new ArrayList<String>(MAX_PER_SEARCH));
        }
        int startIndex = 0;
        String key;
        for (int i = 0; i < searchWord.length(); i++) {
            int count = 0;
            key = searchWord.substring(0, i+1);
            for (int j = startIndex; j < products.length; j++) {
                if (products[j].startsWith(key)) {
                    ans.get(i).add(products[j]);
                    count++;
                    if (count == 1) {
                        startIndex = j;
                    } else if (count == MAX_PER_SEARCH) {
                        break; 
                    }
                }
            }
            if (ans.get(i).isEmpty()) break;
        }
        return ans;
    }

}
