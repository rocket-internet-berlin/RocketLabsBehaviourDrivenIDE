<?php

$autoload = include_once($argv[1]);

for($i = 2; isset($argv[$i]); ++$i) {
    echo $autoload->findFile($argv[$i]) . PHP_EOL;
}
?>