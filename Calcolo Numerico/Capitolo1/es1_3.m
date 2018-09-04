function [] = es1_32(x, itmax)
    % -x: valore passato alla funzione
    % -itmax: massimo numero di iterazione
    format long e
    i = 1;
    while (i<=itmax)
        h = 10^-i;
        f = ((x+h)^4-(x-h)^4)/(2*h);
        str = sprintf('x%d =', -i);
        disp(str), disp(f)
        i = i+1;
    end
end

