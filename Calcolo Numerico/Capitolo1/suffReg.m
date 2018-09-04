function [] = suffReg(x, itmax)
    % -x: valore passato alla funzione
    % -itmax: massimo numero di iterazione
    format long e
    i = 1;
    while (i<=itmax)
        h = 10^-i;
        f = (exp(x+h)-exp(x))/h;
        str = sprintf('x%d =', -i);
        disp(str), disp(f)
        i = i+1;
    end
end