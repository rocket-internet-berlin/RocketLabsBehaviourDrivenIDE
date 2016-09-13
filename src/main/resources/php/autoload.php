<?php

    if (is_file($autoload = getcwd() . '/vendor/autoload.php')) {
        require $autoload;
        exit(0);
    }

    if (is_file($autoload = __DIR__ . '/../vendor/autoload.php')) {
        require($autoload);
        exit(0);
    } elseif (is_file($autoload = __DIR__ . '/../../../autoload.php')) {
        require($autoload);
        exit(0);
    }

    exit(1);

?>