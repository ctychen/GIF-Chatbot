/*Demo use of getting tagged gifs from Tenor API. Example at https://fancy.lerdorf.com/tenor/?q=fancy 
Possible improvement: get a list of results for each query, cache them and generate file names with md5
*/

class Tenor {

    const BASE_URL = "https://api.tenor.com/v1/";


    function __construct(string $key, string $locale="en_US", string $filter="high") {

        $this->key    = $key;

        $this->locale = $locale;

        $this->filter = $filter;

    }


    function fetch(string $url, int $ttl=7200): string {

        $filename = "/var/tmp/tenor_".md5($url);

        if($ts = filemtime($filename)) {

            if(time() - $ts < $ttl) {

                return file_get_contents($filename);

            }

        }

        $result = file_get_contents($url);

        file_put_contents($filename, $result);

        return $result;

    }


    function search($query) {

        $data = [ 'key'    => $this->key,

                  'q'      => $query,

                  'locale' => $this->locale,

                  'contentfilter' => $this->filter,

                  'mediafilter'   => "minimal" ];

        return json_decode($this->fetch(self::BASE_URL.'search?'.http_build_query($data)));

    }

}


$t = new Tenor("H9U6TWFQY1LM");

$result = $t->search($_GET['q']);

foreach($result->results as $img) {

    echo "<img src=\"{$img->media[0]->gif->url}\" width=\"{$img->media[0]->gif->dims[0]}\" height=\"{$img->media[0]->gif->dims[1]}\"/>\n";

}
