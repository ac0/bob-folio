package ex.ac.conversion;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * crypto-compare api adaptor
 * <p>
 * NOTES: Avoiding foolproof JSON parsing minus library support (to ideally cover all legal combos) intentionally,
 * in this iteration atleast. Instead, the approach taken for now is to to perform a very strict regex match
 * so it fails fast, for instance if the format changes from the exchange
 *
 * Also not gone into performance considerations given implementation maturity (e.g. JSON regex). Can add on from
 * keep-alives on one end to even interface change/augmentation on the other end - like the bulk-fetch supported
 * by this specific provider which could help on the latency front.
 */
public class CryptoCompareExchangeAdaptor implements CurrencyExchange {

    private static final String URL_FORMAT = "https://min-api.cryptocompare.com/data/price?fsym=%s&tsyms=%s";

    // See class doc
    private static final Pattern STRICT_SINGLE_TSYM_RESPONSE_PATTERN
            = Pattern.compile("\\{\"([A-Z]*)\":([0-9]*\\.?[0-9]*)\\}");


    @Override
    public BigDecimal getExchangeRate(String fromSym, String toSym) throws UnknownSymbolException {
        String exchangeResponse = getExchangeResponse(fromSym, toSym);

        Matcher responseMatcher = STRICT_SINGLE_TSYM_RESPONSE_PATTERN.matcher(exchangeResponse);

        // Succeeds the tight pattern match; Safe to assume
        if (responseMatcher.matches() && toSym.equals(responseMatcher.group(1))) {
            return new BigDecimal(responseMatcher.group(2));
        }

        // identify the one case that is non system related
        if (exchangeResponse.contains("Thereisnodataforthesymbol" + fromSym)) {
            throw new UnknownSymbolException("Unknown symbol: " + fromSym);
        }

        // rest is as good as system. includes format change
        throw new Error("Exchange error: " + exchangeResponse);
    }

    /*
     * Returns the response (json) content from the exchange with all whitespaces removed to help
     * do the necessary strict match
     *
     * For this exchange a valid json string is responded (with 200 OK) even if input is invalid
     * Therefore if this throws an error, it is a communication or protocol or format breakage error
     * and should not be handled
     */
    private String getExchangeResponse(String fromSym, String toSym) {
        try {
            URL url = new URL(
                    String.format(URL_FORMAT, fromSym, toSym));

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int statusCode = connection.getResponseCode();
            if (statusCode != 200) {
                throw new Error("Exchange communication error: " + statusCode);
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer contentBuffer = new StringBuffer();
            for (String input = in.readLine(); input != null; input = in.readLine()) {
                contentBuffer.append(input);
            }

            in.close();

            return contentBuffer.toString().replaceAll("\\s", "");
        } catch (Exception e) {// Consider all exceptions to be system errors
            throw new Error(e);
        }

    }

}
